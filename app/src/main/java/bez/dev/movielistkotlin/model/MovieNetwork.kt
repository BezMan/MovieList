package bez.dev.movielistkotlin.model

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MovieNetwork : IDataSource {

    private val moviesBaseUrl = "https://api.androidhive.info/"

    private val retrofitMovieInstance = Retrofit.Builder().baseUrl(moviesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val movieCall = retrofitMovieInstance.create(RequestInterface::class.java).fetchJsonData()

    override fun fetchMoviesData(): Single<List<Movie>> {
        return Single.create{ observer ->

            movieCall?.enqueue(object : Callback<List<Movie>>{
                override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                    val movieList = response.body()
                    if (movieList != null){
                        observer.onSuccess(movieList)
                    }
                }

                override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                    observer.onError(t)
                }

            })
        }
    }


    interface RequestInterface {
        @GET("json/movies.json")
        fun fetchJsonData(): Call<List<Movie>>?
    }


}