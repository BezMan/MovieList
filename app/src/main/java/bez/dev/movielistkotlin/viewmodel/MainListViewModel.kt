package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.model.MovieRepository
import io.reactivex.Maybe

class MainListViewModel: ViewModel() {

    private val movieRepository = MovieRepository()

    fun fetchMovies(): Maybe<List<Movie>> {
        return movieRepository.fetchMoviesData()
    }


}