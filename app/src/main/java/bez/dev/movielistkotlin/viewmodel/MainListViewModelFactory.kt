package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bez.dev.movielistkotlin.interfaces.IDataSource

class MainListViewModelFactory(private val dataSource: IDataSource) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainListViewModel(dataSource) as T
    }

}