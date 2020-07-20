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
    private lateinit var mViewModel: MainActivityViewModel


    private val movieListObserver = Observer <MutableList<Movie>> {
        mViewModel.itemList = it
        refreshList()
    }

    private fun refreshList() {
        moviesListAdapter = MoviesListAdapter(this, mViewModel.itemList)
        recyclerViewMain?.adapter = moviesListAdapter
        swipeRefreshLayout.isRefreshing = false
        filterByText(mViewModel.filterText)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()

        initRecyclerView()

        setupSwipeRefresh()

        initSearchView()

        // if `onCreate` is called as a result of configuration change
        if (savedInstanceState==null || !savedInstanceState.getBoolean(ROTATION_CONST)) {
            fetchMoviesData()
        }


    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ROTATION_CONST, isChangingConfigurations);
    }


    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }


    private fun fetchMoviesData() {
        mViewModel.fetchMovies().observe(this, movieListObserver)
    }



    private fun initRecyclerView() {
        recyclerViewMain?.layoutManager = LinearLayoutManager(this)
        recyclerViewMain?.setHasFixedSize(true)
        refreshList()
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


    fun filterByText(text: String) {
        mViewModel.filterText = text
        moviesListAdapter?.filterList(mViewModel.filterText)
    }


    private fun initSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //when the user presses enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //when the text changes
            override fun onQueryTextChange(newText: String?): Boolean {
                if(moviesListAdapter != null) { // on screen rotation do not filter again
                    filterByText(newText!!)
                }
                return true
            }
        })
    }


    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private const val ROTATION_CONST = "isConfigurationChange"
    }

}
