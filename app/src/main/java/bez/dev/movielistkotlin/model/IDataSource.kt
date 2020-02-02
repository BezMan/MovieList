package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData

interface IDataSource {
    fun fetchMoviesData(): LiveData<MutableList<Movie>>

}