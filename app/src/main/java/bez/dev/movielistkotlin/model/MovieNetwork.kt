package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MovieNetwork {

    private val baseUrl = "https://api.androidhive.info/"

    var liveMovieList: MutableLiveData<List<Movie>> = MutableLiveData()

    private val retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RequestInterface::class.java)


    fun fetchMoviesData(): LiveData<List<Movie>> {
        GlobalScope.launch(Dispatchers.IO) {
            val response = retrofit.fetchJsonData()
            if (response.isSuccessful) {
                liveMovieList.postValue(response.body())
            }
        }
        return liveMovieList
    }


    interface RequestInterface {
        @GET("json/movies.json")
        suspend fun fetchJsonData(): Response<List<Movie>>
    }

}