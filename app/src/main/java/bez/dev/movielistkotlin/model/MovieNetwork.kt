package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MovieNetwork : IDataSource {

    private val baseUrl = "https://api.androidhive.info/"

    var liveMovieList = MutableLiveData<MutableList<Movie>>()


    override fun fetchMoviesData(): LiveData<MutableList<Movie>> {
        moviesRequest()
        return liveMovieList
    }


    private fun moviesRequest() {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val call = retrofit.create(RequestInterface::class.java).fetchJsonData()

        call?.enqueue(object : Callback<ArrayList<Movie>> {
            override fun onResponse(call: Call<ArrayList<Movie>>, response: Response<ArrayList<Movie>>) {
                liveMovieList.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Movie>>, t: Throwable) {}
        })
    }


    interface RequestInterface {
        @GET("json/movies.json")
        fun fetchJsonData(): Call<ArrayList<Movie>>?
    }


}