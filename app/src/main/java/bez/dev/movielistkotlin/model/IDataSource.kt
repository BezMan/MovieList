package bez.dev.movielistkotlin.model

import io.reactivex.Single
import java.util.*

interface IDataSource {
    fun fetchMoviesData(): Single<ArrayList<Movie>>

}