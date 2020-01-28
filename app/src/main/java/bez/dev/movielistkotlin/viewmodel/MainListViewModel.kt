package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.interfaces.IMainListDataApi
import bez.dev.movielistkotlin.model.Movie

class MainListViewModel(private val mRemoteDataSource: IMainListDataApi) : ViewModel() {

    fun fetchMoviesData(): MutableLiveData<ArrayList<Movie>> {
        return mRemoteDataSource.fetchMoviesData()
    }

    val observedMoviesList = mRemoteDataSource.getMoviesList()


}