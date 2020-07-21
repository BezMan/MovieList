package bez.dev.movielistkotlin.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bez.dev.movielistkotlin.R
import bez.dev.movielistkotlin.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var movieObj: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        getMovieData()

        initToolbar()

        populateViews()

    }

    //overriding ARROW btn, so we don't recreate MainActivity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun populateViews() {
        detailYear.text = movieObj.releaseYear.toString()
        detailGenre.text = movieObj.genre.toString()
        detailRating.text = movieObj.rating.toString()

        if (movieObj.image.isNotEmpty()) {
            Glide.with(this).load(movieObj.image).placeholder(R.drawable.ic_launcher_foreground).into(detailImage)
        }
    }

    private fun getMovieData() {
        movieObj = intent.getParcelableExtra(MainActivity.EXTRA_MOVIE) as Movie
    }


    private fun initToolbar() {
        detailToolbar_text.text = movieObj.title
        setSupportActionBar(detailToolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


}
