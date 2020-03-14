package bez.dev.movielistkotlin.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bez.dev.movielistkotlin.R
import bez.dev.movielistkotlin.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initToolbar()
        populateViews()

    }

    //overriding ARROW btn, so we don't recreate MainActivity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun populateViews() {
        val movieObj = intent.getParcelableExtra(MainActivity.EXTRA_MOVIE) as Movie

        detailTitle.text = movieObj.title
        detailYear.text = movieObj.releaseYear.toString()
        detailGenre.text = movieObj.genre.toString()
        detailRating.text = movieObj.rating.toString()

        val imageUrl = movieObj.image
        if (imageUrl.isNotEmpty()) {
            Glide.with(this).load(imageUrl).into(detailImage)
        }
    }


    private fun initToolbar() {
        actionBarWeather?.setTitle(R.string.app_name)
        setSupportActionBar(actionBarWeather)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


}
