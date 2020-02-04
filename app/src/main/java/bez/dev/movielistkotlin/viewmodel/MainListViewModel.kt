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

    fun fetchMoviesDB(): Single<List<Movie>> {
        return sourceRepository.fetchMoviesData()
    }

    fun fetchMoviesNetwork(): Single<List<Movie>> {
        return sourceNetwork.fetchMoviesData()
    }

    fun insertListToDB(listMovieObjects: List<Movie>): List<Long> {
        val movieRepository = sourceRepository as MovieRepository
        return movieRepository.insert(listMovieObjects)
    }

}