package bez.dev.movielistkotlin.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MovieNetwork : IDataSource {

    private val moviesBaseUrl = "https://api.androidhive.info/"

    private val retrofitMovieInstance = Retrofit.Builder().baseUrl(moviesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val movieCall = retrofitMovieInstance.create(RequestInterface::class.java)


    override fun fetchMoviesData(): Single<List<Movie>> {
        return movieCall.fetchJsonData()
    }


    interface RequestInterface {
        @GET("json/movies.json")
        fun fetchJsonData(): Single<List<Movie>>
    }


}