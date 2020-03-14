package bez.dev.movielistkotlin.model

import bez.dev.movielistkotlin.App
import bez.dev.movielistkotlin.Utils
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers


class MovieRepository {

    private val movieNetwork = MovieNetwork()
    private val movieDao: MovieDao = App.database.movieDao()


    fun fetchMoviesData(): Maybe<List<Movie>> {
        return if (Utils.isNetworkAvailable(App.appContext))
            fetchFromNetwork()
        else {
            fetchFromDatabase()
        }
    }


    private fun fetchFromDatabase(): Maybe<List<Movie>> {
        return movieDao.getAllMoviesByYear()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap {
                Maybe.just(it) //return DB data
            }
    }


    private fun fetchFromNetwork(): Maybe<List<Movie>> {
        return movieNetwork.fetchMoviesData()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .filter { it.isNotEmpty() }
            .flatMap {
                insertListToDB(it)
                Maybe.just(it)
            }
    }


    private fun insertListToDB(movieList: List<Movie>) {
        return movieDao.insert(movieList)
    }


    fun insert(movie: Movie) {
        return movieDao.insert(movie)
    }


}

