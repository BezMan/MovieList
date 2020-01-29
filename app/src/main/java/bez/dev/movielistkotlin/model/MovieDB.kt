package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import bez.dev.movielistkotlin.App
import bez.dev.movielistkotlin.interfaces.IDataSource

const val KEY_FIRST_RUN = "KEY_FIRST_RUN"

class MovieDB : IDataSource {
    private val movieDatabase: MovieDatabase = App.database
    private val movieDao: MovieDao = movieDatabase.movieDao()
    private val allMoviesByYear: LiveData<MutableList<Movie>>

    init {
        allMoviesByYear = movieDao.getAllMoviesByYear()
    }

    override fun fetchMoviesData(): LiveData<MutableList<Movie>> {
        return movieDao.getAllMoviesByYear()
    }

}

