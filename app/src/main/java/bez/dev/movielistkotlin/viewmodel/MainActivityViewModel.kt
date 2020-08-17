package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.model.Movie
import bez.dev.movielistkotlin.model.MovieRepository

class MainActivityViewModel: ViewModel() {

    var searchText: String = ""
    var itemList: List<Movie> = mutableListOf()
    private val movieRepository = MovieRepository()

    fun fetchMovies(): LiveData<List<Movie>> {
        return movieRepository.fetchMoviesData()
    }


}