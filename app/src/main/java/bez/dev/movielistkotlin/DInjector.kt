package bez.dev.movielistkotlin

import bez.dev.movielistkotlin.interfaces.IDataSource
import bez.dev.movielistkotlin.model.MovieNetwork
import bez.dev.movielistkotlin.model.MovieRepository
import bez.dev.movielistkotlin.viewmodel.MainListViewModel

object DInjector {

    fun getViewModel(): MainListViewModel {

        return MainListViewModel(injectRepository(), injectNetwork())
    }


    private fun injectNetwork(): IDataSource {
        return MovieNetwork()
    }

    private fun injectRepository(): IDataSource {
        return MovieRepository()
    }

}
