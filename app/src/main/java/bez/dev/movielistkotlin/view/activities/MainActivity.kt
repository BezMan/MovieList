package bez.dev.movielistkotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bez.dev.movielistkotlin.DInjector
import bez.dev.movielistkotlin.R
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.view.adapters.MoviesListAdapter
import bez.dev.movielistkotlin.viewmodel.MainListViewModel
import bez.dev.movielistkotlin.viewmodel.MainListViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MoviesListAdapter.ItemClickListener {

    private lateinit var listMovieObjects: ArrayList<Movie>
    private lateinit var moviesListAdapter: MoviesListAdapter

    private lateinit var searchView: SearchView

    private lateinit var mViewModel: MainListViewModel

    private val dataObserver: Observer<ArrayList<Movie>> = Observer { list: ArrayList<Movie>? ->
        dataCallback(list)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBarChoose.setTitle(R.string.app_name)
        setActionBar(actionBarChoose)

        listMovieObjects = ArrayList()

        configureRecyclerView()
        initViewModel()
        setObservers()


        fetchAllCitiesData()

    }


    private fun initViewModel() {
        mViewModel = DInjector.getViewModel()
        mViewModel =
            ViewModelProvider(this, MainListViewModelFactory(DInjector.getMainRepository())).get(
                MainListViewModel::class.java
            )
    }

    private fun setObservers() {
        mViewModel.observedMoviesList.observe(this, dataObserver)
    }

    private fun dataCallback(data: ArrayList<Movie>?) {
        listMovieObjects.clear()
        listMovieObjects = ArrayList(data!!)

        moviesListAdapter = MoviesListAdapter(this, listMovieObjects)
        moviesListAdapter.setClickListener(this)
        recyclerViewMain?.adapter = moviesListAdapter

        moviesListAdapter.notifyDataSetChanged()
    }


    private fun fetchAllCitiesData() {
        mViewModel.fetchMoviesData()
    }


    private fun configureRecyclerView() {
        recyclerViewMain?.layoutManager = LinearLayoutManager(this)
        recyclerViewMain?.setHasFixedSize(true)
    }


    fun searchFilter(text: String) {
        moviesListAdapter.filter(text)
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


//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return when (item?.itemId) {
//            R.id.icUnit -> {
//                storedWeatherUnit = if (storedWeatherUnit == CELSIUS) FAHRENHEIT else CELSIUS
//                SharedPrefs.saveStringData(SharedPrefs.UNIT_KEY, storedWeatherUnit)
//                recreate()
//
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        val obj = listMovieObjects[position]
        intent.putExtra(EXTRA_MOVIE, obj)
        startActivity(intent)
    }


    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }

}
