package bez.dev.movielistkotlin

import bez.dev.movielistkotlin.interfaces.IDataSource
import bez.dev.movielistkotlin.model.NoteRepository
import bez.dev.movielistkotlin.model.WeatherNetwork
import bez.dev.movielistkotlin.viewmodel.MainListViewModel

object DInjector {

    fun getViewModel(): MainListViewModel {
//        return MainListViewModel(getSourceDB())
        return MainListViewModel(getSourceNetwork())
    }


    fun getSourceNetwork(): IDataSource {
        return WeatherNetwork()
    }

    fun getSourceDB(): IDataSource {
        return NoteRepository()
    }

}
