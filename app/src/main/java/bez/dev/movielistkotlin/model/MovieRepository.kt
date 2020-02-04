package bez.dev.movielistkotlin.model

import bez.dev.movielistkotlin.App
import io.reactivex.Single


class MovieRepository : IDataSource {

    private val movieDatabase: MovieDatabase = App.database
    private val movieDao: MovieDao = movieDatabase.movieDao()


    override fun fetchMoviesData(): Single<ArrayList<Movie>> {
        return movieDao.getAllMoviesByYear()
    }

    suspend fun insert(movie: Movie): Long {
        return movieDao.insert(movie)
    }

    suspend fun insert(movieList: ArrayList<Movie>): List<Long> {
        return movieDao.insert(movieList)
    }

}

