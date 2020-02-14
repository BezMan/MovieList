package bez.dev.movielistkotlin.model

import bez.dev.movielistkotlin.App
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class MovieRepository{

    private val movieDatabase: MovieDatabase = App.database
    private val movieNetwork = MovieNetwork()
    private val movieDao: MovieDao = movieDatabase.movieDao()


    fun fetchMoviesData(): Maybe<List<Movie>> {
        return movieDao.getAllMoviesByYear()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .filter { list -> list.isEmpty() }
            .flatMap {
                fetchFromNetwork()
            }
    }

    private fun fetchFromNetwork(): Maybe<List<Movie>> {
        return  movieNetwork.fetchMoviesData()
    }


    fun insert(movie: Movie): Single<Long> {
        return movieDao.insert(movie)
    }

    fun insertListToDB(movieList: List<Movie>): Single<List<Long>> {
        return movieDao.insert(movieList)
    }

}

