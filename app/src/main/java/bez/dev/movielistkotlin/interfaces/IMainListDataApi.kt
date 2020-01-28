package bez.dev.movielistkotlin.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bez.dev.movielistkotlin.model.Movie

interface IMainListDataApi {
    fun fetchMoviesData(): MutableLiveData<ArrayList<Movie>>
    fun getMoviesList(): LiveData<ArrayList<Movie>>

}