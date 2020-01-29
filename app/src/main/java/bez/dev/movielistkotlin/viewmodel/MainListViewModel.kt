package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.interfaces.IDataSource
import bez.dev.movielistkotlin.model.Movie

class MainListViewModel(private val mRemoteDataSource: IDataSource) : ViewModel() {

    fun fetchMoviesData(): LiveData<MutableList<Movie>> {
        return mRemoteDataSource.fetchMoviesData()
    }

    val observedMoviesList = mRemoteDataSource.fetchMoviesData()


}