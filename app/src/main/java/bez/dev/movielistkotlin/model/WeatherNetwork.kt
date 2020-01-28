package bez.dev.movielistkotlin.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bez.dev.movielistkotlin.interfaces.IMainListDataApi
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class WeatherNetwork : IMainListDataApi {

    private val URL_BASE = "https://api.androidhive.info/"
    private val FORMAT = "json/"
    private val FILE = "movies.json"

    var movieList = ArrayList<Movie>()
    var liveMovieList = MutableLiveData<ArrayList<Movie>>()


    override fun fetchMoviesData(): MutableLiveData<ArrayList<Movie>> {
        val url = "$URL_BASE$FORMAT$FILE"

        movieList.clear()
        liveMovieList.value = movieList
        moviesHttpRequest(url, dataCallback())

        return liveMovieList
    }


    private fun dataCallback(): HttpResponse {
        return object : HttpResponse {
            override fun httpResponseSuccess(response: String) {
                Log.d("responseresponse", "" + response)
                val cityData = Gson().fromJson(response, Movie::class.java)
//                if (cityData.name.isNotEmpty()) {
//                    val cityObj = CityObj(cityData.name
//                            , makeIconURL(cityData.weather[0].icon)
//                            , cityData.weather[0].description
//                            , cityData.main.temp_min.toString()
//                            , cityData.main.temp_max.toString())


//                    cityList.add(cityObj)
                movieList.add(cityData)
                liveMovieList.postValue(movieList)


            }
        }
    }

    override fun getMoviesList(): LiveData<ArrayList<Movie>> {
        return liveMovieList
    }


    private fun makeIconURL(icon: String): String {
        return "${URL_BASE}img/w/$icon.png"
    }


    private fun moviesHttpRequest(url: String, httpResponse: HttpResponse) {
        Log.d("URL_REQUEST", url)
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("HTTP_REQUEST", response?.body().toString())
                httpResponse.httpResponseSuccess(response?.body()?.string()!!)
            }

            override fun onFailure(call: Call?, e: IOException?) {
            }
        })
    }


    interface HttpResponse {
        fun httpResponseSuccess(response: String)
    }

}