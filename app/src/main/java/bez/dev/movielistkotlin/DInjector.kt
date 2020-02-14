package bez.dev.movielistkotlin

import bez.dev.movielistkotlin.viewmodel.MainListViewModel

object DInjector {

    fun getViewModel(): MainListViewModel {

        return MainListViewModel()
    }


//    private fun injectNetwork(): IDataSource {
//        return MovieNetwork()
//    }
//
//    private fun injectRepository(): IDataSource {
//        return MovieRepository()
//    }

}
