package bez.dev.movielistkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bez.dev.movielistkotlin.interfaces.IMainListDataApi
import bez.dev.movielistkotlin.model.CityObj

class MainListViewModel(private val mRemoteDataSource: IMainListDataApi) : ViewModel() {

    fun getWeatherByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<CityObj>> {
        return mRemoteDataSource.getWeatherByCity(cityName, unit)
    }

    val observedCityList = mRemoteDataSource.getCityList()


}