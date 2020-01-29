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

class MoviesListAdapter(private var context: Context, itemList: MutableList<Movie>) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {
    private var filteredList: MutableList<Movie> = itemList

    companion object {
        var mClickListener: ItemClickListener? = null
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, p0, false)
        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (!filteredList.isNullOrEmpty()) {
            viewHolder.tvTitle.text = filteredList[position].title
            viewHolder.tvReleaseYear.text = filteredList[position].releaseYear.toString()

            Glide.with(context).load(filteredList[position].image).into(viewHolder.iconImg)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.count()
    }


    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }


    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var tvTitle: TextView = view.tvTitle
        var tvReleaseYear: TextView = view.tvReleaseYear
        var iconImg: ImageView = view.iconImg
        private var cardView: CardView = view.cardView

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mClickListener?.onItemClick(adapterPosition)
        }

    }
}