package bez.dev.movielistkotlin.view.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
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

    private lateinit var moviesListAdapter: MoviesListAdapter
    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var searchView: SearchView


    private val observer = Observer <MutableList<Movie>> {
        mViewModel.itemList = it
        refreshList()
    }

    private fun refreshList() {
        moviesListAdapter = MoviesListAdapter(this, mViewModel.itemList)
        recyclerViewMain?.adapter = moviesListAdapter
        swipeRefreshLayout.isRefreshing = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()

        initActionBar()

        initRecyclerView()

        setupSwipeRefresh()

        // if `onCreate` is called as a result of configuration change
        if (savedInstanceState==null || !savedInstanceState.getBoolean(ROTATION_CONST)) {
            fetchMoviesData()
        }


    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ROTATION_CONST, isChangingConfigurations);
    }


    private fun initActionBar() {
        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }


//    private fun refreshList(listData: List<Movie>) {
//
//        if (!listData.isNullOrEmpty()) {
//            listMovieObjects.clear()
//
//            listMovieObjects = listData as ArrayList<Movie>
//
//            moviesListAdapter = MoviesListAdapter(this, listMovieObjects)
//            recyclerViewMain?.adapter = moviesListAdapter
//
//            listMovieObjects.sortWith(Comparator { lhs, rhs ->
//                // -1 == less than (shown first), 1 == greater than, 0 == equal
//                if (lhs.releaseYear > rhs.releaseYear) -1 /*else if (lhs.title < rhs.title) 1*/ else 0
//            })
//
//            moviesListAdapter.notifyDataSetChanged()
//
//        }
//        else{
//            network_error_message.visibility = View.VISIBLE
//        }
//        swipeRefreshLayout.isRefreshing = false
//    }


    private fun fetchMoviesData() {
        mViewModel.fetchMovies().observe(this, observer)
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


    fun searchFilter(text: String) {
        moviesListAdapter.searchFilter(text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_choose, menu)

        val itemSearch = menu?.findItem(R.id.icSearch)
        initSearchView(itemSearch)

        return super.onCreateOptionsMenu(menu)
    }

    private fun initSearchView(itemSearch: MenuItem?) {
        searchView = itemSearch?.actionView as SearchView
        searchView.queryHint = "Search movie..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //when the user presses enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //when the text changes
            override fun onQueryTextChange(newText: String?): Boolean {
                searchFilter(newText!!)
                return true
            }
        })
    }


    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private const val ROTATION_CONST = "isConfigurationChange"
    }

}
