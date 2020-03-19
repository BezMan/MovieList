package bez.dev.movielistkotlin.view.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val mViewModel: MainListViewModel =  MainListViewModel()

    private var bag = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)

        listMovieObjects = ArrayList()

        initRecyclerView()
        setupSwipeRefresh()

        fetchMoviesData()

    }


    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }


    private fun refreshList(listData: List<Movie>) {

        if (!listData.isNullOrEmpty()) {
            listMovieObjects.clear()

            listMovieObjects = listData as ArrayList<Movie>

            moviesListAdapter = MoviesListAdapter(this, listMovieObjects)
            recyclerViewMain?.adapter = moviesListAdapter

            listMovieObjects.sortWith(Comparator { lhs, rhs ->
                // -1 == less than (shown first), 1 == greater than, 0 == equal
                if (lhs.releaseYear > rhs.releaseYear) -1 /*else if (lhs.title < rhs.title) 1*/ else 0
            })

            moviesListAdapter.notifyDataSetChanged()

        }
        else{
            network_error_message.visibility = View.VISIBLE
        }
        swipeRefreshLayout.isRefreshing = false
    }


    private fun fetchMoviesData() {
        val disposable = mViewModel.fetchMovies().
            observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    refreshList(it)
                },
                { error -> Log.e("callbackNetwork","$error") }
            )
        bag.add(disposable)

    }



    private fun initRecyclerView() {
        recyclerViewMain?.layoutManager = LinearLayoutManager(this)
        recyclerViewMain?.setHasFixedSize(true)

        moviesListAdapter = MoviesListAdapter(this, listMovieObjects)
        recyclerViewMain?.adapter = moviesListAdapter
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            fetchMoviesData()
        }
    }

    override fun onItemClick(movie: Movie, imageView: ImageView) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        val options =  ActivityOptions.makeSceneTransitionAnimation(
            this, imageView ,getString(R.string.shared_animation_imageview))  // The transition name to be matched in Activity B.
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
    }

}
