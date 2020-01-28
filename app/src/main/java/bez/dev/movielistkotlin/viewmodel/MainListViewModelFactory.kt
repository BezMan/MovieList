package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bez.dev.movielistkotlin.interfaces.IMainListDataApi

class MainListViewModelFactory(private val dataSource: IMainListDataApi) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainListViewModel(dataSource) as T
    }

}