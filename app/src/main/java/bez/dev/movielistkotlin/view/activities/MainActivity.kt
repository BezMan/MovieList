package bez.dev.movielistkotlin.view.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bez.dev.movielistkotlin.R
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.view.adapters.MoviesListAdapter
import bez.dev.movielistkotlin.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MoviesListAdapter.ItemClickListener {

    private var moviesListAdapter: MoviesListAdapter? = null
    private lateinit var viewModel: MainActivityViewModel


    private val movieListObserver = Observer <List<Movie>> {
        viewModel.itemList = it
        refreshList()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()

        initSearchListener()

        initRecyclerView()

        setupSwipeRefresh()

        fetchAndDisplayData(savedInstanceState)

    }


    /** If `onCreate` is called as a result of rotation/configuration change,
     * we only refresh UI without another network call */

    private fun fetchAndDisplayData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null || !savedInstanceState.getBoolean(IS_ROTATED)) {
            fetchMoviesData()
        } else {
            refreshList()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_ROTATED, isChangingConfigurations)
    }


    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
        } else {
            super.onBackPressed()
        }
    }


    private fun refreshList() {
        moviesListAdapter = MoviesListAdapter(this, viewModel.itemList)
        recyclerViewMain?.adapter = moviesListAdapter
        swipeRefreshLayout.isRefreshing = false
        moviesListAdapter?.filterList(viewModel.searchText)
    }


    private fun initSearchListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //when the user presses enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //when the text changes
            override fun onQueryTextChange(newText: String?): Boolean {
                if(moviesListAdapter != null) { // on screen rotation do not filter again
                    viewModel.searchText = newText!!
                    moviesListAdapter?.filterList(newText)
                }
                return true
            }
        })
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }


    private fun fetchMoviesData() {
        swipeRefreshLayout.isRefreshing = true
        viewModel.fetchMovies().observe(this, movieListObserver)
    }



    private fun initRecyclerView() {
        recyclerViewMain?.layoutManager = LinearLayoutManager(this)
        recyclerViewMain?.setHasFixedSize(true)
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            fetchMoviesData()
        }
    }

    override fun onItemClick(movie: Movie, viewHolder: MoviesListAdapter.ViewHolder) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)

        val options = ActivityOptions.makeSceneTransitionAnimation(this,
            Pair.create<View, String>(viewHolder.imageView, getString(R.string.shared_animation_imageview)),
            Pair.create<View, String>(viewHolder.tvTitle, getString(R.string.shared_animation_title))
        )

        startActivity(intent, options.toBundle())

    }


    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private const val IS_ROTATED = "isConfigurationChange"
    }

}
