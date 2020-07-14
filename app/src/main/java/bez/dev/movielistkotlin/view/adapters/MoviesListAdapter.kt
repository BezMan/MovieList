package bez.dev.movielistkotlin.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import bez.dev.movielistkotlin.R
import bez.dev.movielistkotlin.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MoviesListAdapter(private var context: ItemClickListener, itemList: MutableList<Movie>) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    private var filteredList: MutableList<Movie> = itemList
    private var fullList: MutableList<Movie> = itemList

    private var mClickListener: ItemClickListener = context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(context as Context).inflate(R.layout.list_item_movie, p0, false)
        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (!filteredList.isNullOrEmpty()) {
            viewHolder.tvTitle.text = filteredList[position].title
            viewHolder.tvReleaseYear.text = filteredList[position].releaseYear.toString()

            val imageUrl = filteredList[position].image
            if(imageUrl.isNotEmpty()) {
                Glide.with(context as Context).load(imageUrl).placeholder(R.drawable.ic_launcher_foreground).into(viewHolder.imageView)
            }
            viewHolder.cardView.setOnClickListener {
                mClickListener.onItemClick(filteredList[position], viewHolder)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.count()
    }


    fun searchFilter(query: String) {
        if (query.isBlank()) {
            filteredList = ArrayList(fullList)
        } else {
            filteredList.clear()
            for (item in fullList) {
                if (item.title.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item)
                }
            }
        }

        filteredList.sortWith(Comparator { lhs, rhs ->
            // -1 == less than (shown first), 1 == greater than, 0 == equal
            if (lhs.releaseYear > rhs.releaseYear) -1 /*else if (lhs.title < rhs.title) 1*/ else 0
        })

        notifyDataSetChanged()
    }


    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(movie: Movie, viewHolder: ViewHolder)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.tvTitle
        var tvReleaseYear: TextView = view.tvReleaseYear
        var imageView: ImageView = view.listItemImage
        var cardView: CardView = view.cardView
    }
}