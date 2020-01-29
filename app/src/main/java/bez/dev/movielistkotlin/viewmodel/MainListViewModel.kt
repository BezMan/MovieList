package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.interfaces.IDataSource
import bez.dev.movielistkotlin.model.Movie

class MainListViewModel(private val sourceDB: IDataSource, private val sourceNetwork: IDataSource) :
    ViewModel() {

    val observedMoviesList = fetchMoviesData()


    fun fetchMoviesData(): LiveData<MutableList<Movie>> {
        return sourceNetwork.fetchMoviesData()
    }

}