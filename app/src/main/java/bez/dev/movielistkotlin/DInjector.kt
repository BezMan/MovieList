package bez.dev.movielistkotlin

import bez.dev.movielistkotlin.interfaces.IDataSource
import bez.dev.movielistkotlin.model.MovieNetwork
import bez.dev.movielistkotlin.model.MovieRepository
import bez.dev.movielistkotlin.viewmodel.MainListViewModel

object DInjector {

    fun getViewModel(): MainListViewModel {

        return MainListViewModel(getSourceRepository(), getSourceNetwork())
    }


    private fun getSourceNetwork(): IDataSource {
        return MovieNetwork()
    }

    private fun getSourceRepository(): IDataSource {
        return MovieRepository()
    }

}
