package bez.dev.movielistkotlin

import bez.dev.movielistkotlin.interfaces.IMainListDataApi
import bez.dev.movielistkotlin.model.WeatherNetwork
import bez.dev.movielistkotlin.viewmodel.MainListViewModel

object DInjector {

    fun getViewModel(): MainListViewModel {
        return MainListViewModel(getMainRepository())
    }


    fun getMainRepository(): IMainListDataApi {
        return WeatherNetwork()
    }

}
