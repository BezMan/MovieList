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

    private lateinit var mClickListener: ItemClickListener

    init {
        setOnItemClickListener(context)
    }

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

            Glide.with(context as Context).load(filteredList[position].image)
                .into(viewHolder.iconImg)
            viewHolder.cardView.setOnClickListener {
                mClickListener.onItemClick(filteredList[position])

            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.count()
    }


    // allows clicks events to be caught
    private fun setOnItemClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }


    fun searchFilter(query: String) {
        if (query == "") {
            filteredList = ArrayList(fullList)
        } else {
            filteredList.clear()
            var search = query
            search = search.toLowerCase()
            for (item in fullList) {
                val cityName = item.title.toLowerCase()
                if (cityName.contains(search)) {
                    filteredList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }


    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(position: Movie)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.tvTitle
        var tvReleaseYear: TextView = view.tvReleaseYear
        var iconImg: ImageView = view.iconImg
        var cardView: CardView = view.cardView
    }
}