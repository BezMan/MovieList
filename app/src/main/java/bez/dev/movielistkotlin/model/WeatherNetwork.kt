package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bez.dev.movielistkotlin.interfaces.IMainListDataApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class WeatherNetwork : IMainListDataApi {

    private val URL_BASE = "https://api.androidhive.info/"

    var liveMovieList = MutableLiveData<ArrayList<Movie>>()


    override fun fetchMoviesData(): MutableLiveData<ArrayList<Movie>> {
        moviesHttpRequest()

        return liveMovieList
    }


    override fun getMoviesList(): LiveData<ArrayList<Movie>> {
        return liveMovieList
    }

    private fun moviesHttpRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val service = retrofit.create(RequestInterface::class.java)
        val call = service.getJSON()

        call?.enqueue(object : Callback<ArrayList<Movie>> {
            override fun onResponse(
                call: Call<ArrayList<Movie>>,
                response: Response<ArrayList<Movie>>
            ) {
                liveMovieList.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Movie>>, t: Throwable) {
//                weatherData!!.text = t.message
            }
        })
    }


    interface RequestInterface {
        @GET("json/movies.json")
        fun getJSON(): Call<ArrayList<Movie>>?
    }


}