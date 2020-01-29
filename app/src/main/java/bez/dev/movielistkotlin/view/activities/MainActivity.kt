package bez.dev.movielistkotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bez.dev.movielistkotlin.DInjector
import bez.dev.movielistkotlin.R
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.view.adapters.MoviesListAdapter
import bez.dev.movielistkotlin.viewmodel.MainListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MoviesListAdapter.ItemClickListener {

    private lateinit var listMovieObjects: MutableList<Movie>
    private lateinit var moviesListAdapter: MoviesListAdapter

    private lateinit var searchView: SearchView

    private lateinit var mViewModel: MainListViewModel

    private val databaseObserver: Observer<MutableList<Movie>> =
        Observer { list: MutableList<Movie> ->
            callbackDB(list)
    }

    private val networkObserver: Observer<MutableList<Movie>> =
        Observer { list: MutableList<Movie> ->
            callbackNetwork(list)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)

        listMovieObjects = ArrayList()

        configureRecyclerView()
        initViewModel()
        setObservers()

        fetchAllCitiesData()

    }


    private fun initViewModel() {
        mViewModel = DInjector.getViewModel()
    }


    private fun setObservers() {
        mViewModel.fetchMoviesDB().observe(this, databaseObserver)
    }


    private fun callbackNetwork(listData: MutableList<Movie>) {
        CoroutineScope(Dispatchers.Default).launch {
            mViewModel.insertListToDB(listData)
        }

    }


    private fun callbackDB(listData: MutableList<Movie>) {

        if (listData.isNullOrEmpty()) {
            mViewModel.fetchMoviesNetwork().observe(this, networkObserver)
        } else {
            listMovieObjects.clear()
            listMovieObjects = listData

            moviesListAdapter = MoviesListAdapter(this, listMovieObjects)
            recyclerViewMain?.adapter = moviesListAdapter

            moviesListAdapter.notifyDataSetChanged()
        }
    }

    private fun fetchAllCitiesData() {
        mViewModel.fetchMoviesDB()
    }


    private fun configureRecyclerView() {
        recyclerViewMain?.layoutManager = LinearLayoutManager(this)
        recyclerViewMain?.setHasFixedSize(true)
    }


    override fun onItemClick(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
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
        searchView.queryHint = "Write city..."

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
    }

}
