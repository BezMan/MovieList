package bez.dev.movielistkotlin.model

import bez.dev.movielistkotlin.App
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers


class MovieRepository{

    private val movieDatabase: MovieDatabase = App.database
    private val movieNetwork = MovieNetwork()
    private val movieDao: MovieDao = movieDatabase.movieDao()


    fun fetchMoviesData(): Flowable<List<Movie>> {
        return movieDao.getAllMoviesByYear()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap {
                if (it.isEmpty())
                    fetchFromNetwork()
                else
                    Flowable.just(it) //return DB data
            }
    }

    private fun fetchFromNetwork(): Flowable<List<Movie>> {
        return  movieNetwork.fetchMoviesData()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .filter { it.isNotEmpty() }
            .flatMap {
                insertListToDB(it)
                Flowable.just(it)
            }
    }


    fun refreshMoviesData(): Flowable<List<Movie>> {
        return fetchFromNetwork()
    }


    private fun insertListToDB(movieList: List<Movie>){
        return movieDao.insert(movieList)
    }


    fun insert(movie: Movie) {
        return movieDao.insert(movie)
    }


}

