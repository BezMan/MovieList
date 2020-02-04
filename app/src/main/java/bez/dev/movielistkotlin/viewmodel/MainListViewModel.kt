package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.model.IDataSource
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.model.MovieRepository
import io.reactivex.Single

class MainListViewModel(
    private val sourceRepository: IDataSource,
    private val sourceNetwork: IDataSource
) : ViewModel() {

    fun fetchMoviesDB(): Single<ArrayList<Movie>> {
        return sourceRepository.fetchMoviesData()
    }

    fun fetchMoviesNetwork(): Single<ArrayList<Movie>> {
        return sourceNetwork.fetchMoviesData()
    }

    suspend fun insertListToDB(listMovieObjects: ArrayList<Movie>): List<Long> {
        val movieRepository = sourceRepository as MovieRepository
        return movieRepository.insert(listMovieObjects)
    }

}