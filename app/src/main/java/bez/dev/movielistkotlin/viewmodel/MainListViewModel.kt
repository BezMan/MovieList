package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.interfaces.IDataSource
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.model.MovieRepository

class MainListViewModel(
    private val sourceRepository: IDataSource,
    private val sourceNetwork: IDataSource
) : ViewModel() {

    fun fetchMoviesDB(): LiveData<MutableList<Movie>> {
        return sourceRepository.fetchMoviesData()
    }

    fun fetchMoviesNetwork(): LiveData<MutableList<Movie>> {
        return sourceNetwork.fetchMoviesData()
    }

    suspend fun insert(listMovieObjects: MutableList<Movie>): List<Long> {
        val movieRepository = sourceRepository as MovieRepository
        return movieRepository.insert(listMovieObjects)
    }

}