package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import bez.dev.movielistkotlin.App
import bez.dev.movielistkotlin.interfaces.IDataSource


class MovieRepository : IDataSource {

    private val movieDatabase: MovieDatabase = App.database
    private val movieDao: MovieDao = movieDatabase.movieDao()


    override fun fetchMoviesData(): LiveData<MutableList<Movie>> {
        return movieDao.getAllMoviesByYear()
    }

    suspend fun insert(movie: Movie): Long {
        return movieDao.insert(movie)
    }

    suspend fun insert(movieList: MutableList<Movie>): List<Long> {
        return movieDao.insert(movieList)
    }

}

