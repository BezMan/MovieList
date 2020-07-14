package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.model.MovieRepository

class MainActivityViewModel: ViewModel() {

    private val movieRepository = MovieRepository()

    fun fetchMovies(): LiveData<MutableList<Movie>> {
        return movieRepository.fetchMoviesData()
    }


}