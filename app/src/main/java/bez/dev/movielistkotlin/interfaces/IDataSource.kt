package bez.dev.movielistkotlin.interfaces

import androidx.lifecycle.LiveData
import bez.dev.movielistkotlin.model.Movie

interface IDataSource {
    fun fetchMoviesData(): LiveData<MutableList<Movie>>

}