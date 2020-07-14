package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import bez.dev.movielistkotlin.App
import bez.dev.movielistkotlin.Utils


class MovieRepository {

    private val movieNetwork = MovieNetwork()
    private val movieDao: MovieDao = App.database.movieDao()


    fun fetchMoviesData(): LiveData<MutableList<Movie>> {
        return if (Utils.isNetworkAvailable(App.appContext))
            return fetchFromNetwork()
        else {
            fetchFromDatabase()
        }
    }


    private fun fetchFromDatabase(): LiveData<MutableList<Movie>> {
        return movieDao.getAllMoviesByYear()
    }


    private fun fetchFromNetwork(): LiveData<MutableList<Movie>> {
        return movieNetwork.fetchMoviesData()
    }


    private fun insertListToDB(movieList: List<Movie>) {
        movieDao.insert(movieList)
    }


    fun insert(movie: Movie) {
        movieDao.insert(movie)
    }


}

