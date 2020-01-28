package bez.dev.movielistkotlin.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bez.dev.movielistkotlin.model.Movie

interface IMainListDataApi {
    fun getWeatherByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<Movie>>
    fun getCityList(): LiveData<ArrayList<Movie>>

}