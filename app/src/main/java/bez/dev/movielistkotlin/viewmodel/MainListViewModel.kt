package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.model.MovieRepository
import io.reactivex.Flowable

class MainListViewModel: ViewModel() {

    private val movieRepository = MovieRepository()

    fun fetchMovies(): Flowable<List<Movie>> {
        return movieRepository.fetchMoviesData()
    }

    fun refreshMoviesData(): Flowable<List<Movie>> {
        return movieRepository.refreshMoviesData()
    }


}