package bez.dev.movielistkotlin.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bez.dev.movielistkotlin.model.CityObj

interface IMainListDataApi {
    fun getWeatherByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<CityObj>>
    fun getCityList(): LiveData<ArrayList<CityObj>>

}