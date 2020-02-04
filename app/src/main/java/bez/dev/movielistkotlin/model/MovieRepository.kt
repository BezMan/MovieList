package bez.dev.movielistkotlin.model

import bez.dev.movielistkotlin.App
import io.reactivex.Single


class MovieRepository : IDataSource {

    private val movieDatabase: MovieDatabase = App.database
    private val movieDao: MovieDao = movieDatabase.movieDao()


    override fun fetchMoviesData(): Single<List<Movie>> {
        return movieDao.getAllMoviesByYear()
    }

    fun insert(movie: Movie): Single<Long> {
        return movieDao.insert(movie)
    }

    fun insert(movieList: List<Movie>): Single<List<Long>> {
        return movieDao.insert(movieList)
    }

}

