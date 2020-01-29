package bez.dev.movielistkotlin

import bez.dev.movielistkotlin.interfaces.IDataSource
import bez.dev.movielistkotlin.model.MovieDB
import bez.dev.movielistkotlin.model.MovieNetwork
import bez.dev.movielistkotlin.viewmodel.MainListViewModel

object DInjector {

    fun getViewModel(): MainListViewModel {

        return MainListViewModel(getSourceDB(), getSourceNetwork())
    }


    private fun getSourceNetwork(): IDataSource {
        return MovieNetwork()
    }

    private fun getSourceDB(): IDataSource {
        return MovieDB()
    }

}
