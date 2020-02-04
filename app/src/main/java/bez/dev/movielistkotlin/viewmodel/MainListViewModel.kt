package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.model.IDataSource
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.model.MovieNetwork
import bez.dev.movielistkotlin.model.MovieRepository
import io.reactivex.Single

class MainListViewModel(
    private val sourceRepository: IDataSource,
    private val sourceNetwork: IDataSource
) : ViewModel() {

    fun fetchMoviesDB(): LiveData<MutableList<Movie>> {
        return sourceRepository.fetchMoviesData()
    }

    fun fetchMoviesNetwork(): Single<ArrayList<Movie>> {
        val movieNetwork = sourceNetwork as MovieNetwork
        return movieNetwork.fetchMoviesNetwork()
    }

    suspend fun insertListToDB(listMovieObjects: MutableList<Movie>): List<Long> {
        val movieRepository = sourceRepository as MovieRepository
        return movieRepository.insert(listMovieObjects)
    }

}