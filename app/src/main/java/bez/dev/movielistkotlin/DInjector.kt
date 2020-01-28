package bez.dev.movielistkotlin

import bez.dev.movielistkotlin.interfaces.IMainListDataApi
import bez.dev.movielistkotlin.model.WeatherNetwork

object DInjector {

    fun getRepository(): IMainListDataApi {
        return WeatherNetwork()
    }

}
