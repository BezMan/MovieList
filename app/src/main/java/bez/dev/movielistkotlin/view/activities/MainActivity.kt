package bez.dev.movielistkotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import bez.dev.movielistkotlin.DInjector
import bez.dev.movielistkotlin.R
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.view.adapters.MoviesListAdapter
import bez.dev.movielistkotlin.viewmodel.MainListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MoviesListAdapter.ItemClickListener {

    private lateinit var listMovieObjects: ArrayList<Movie>
    private lateinit var moviesListAdapter: MoviesListAdapter

    private lateinit var searchView: SearchView

    private lateinit var mViewModel: MainListViewModel

    private var bag = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)

        listMovieObjects = ArrayList()

        initRecyclerView()
        initSwipe()
        initViewModel()

        fetchAllCitiesData()

    }


    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }



    private fun initViewModel() {
        mViewModel = DInjector.getViewModel()
    }


    private fun callbackNetwork(listData: List<Movie>) {
        mViewModel.insertListToDB(listData)

//        mPullToRefreshView.setRefreshing(false)
    }


    private fun callbackDB(listData: List<Movie>) {

        if (listData.isNullOrEmpty()) {
            fetchFromNetwork()
        } else {
            listMovieObjects.clear()
            listMovieObjects = listData as ArrayList<Movie>

            moviesListAdapter = MoviesListAdapter(this, listMovieObjects)
            recyclerViewMain?.adapter = moviesListAdapter
            moviesListAdapter.notifyDataSetChanged()
        }
    }


    private fun fetchFromNetwork() {
        val disposable = mViewModel.fetchMoviesNetwork()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { movieList ->
                    callbackNetwork(movieList)
                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

                })
        bag.add(disposable)
    }


    private fun fetchAllCitiesData() {
        val disposable = mViewModel.fetchMoviesDB()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { movieList ->
                    callbackDB(movieList)
                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

                })
        bag.add(disposable)
    }


    private fun initRecyclerView() {
        recyclerViewMain?.layoutManager = LinearLayoutManager(this)
        recyclerViewMain?.setHasFixedSize(true)
    }

    private fun initSwipe() {
        mPullToRefreshView.setOnRefreshListener {
            fetchFromNetwork()
            mPullToRefreshView.postDelayed(
                { mPullToRefreshView.setRefreshing(false) },
                2000
            )
        }
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
