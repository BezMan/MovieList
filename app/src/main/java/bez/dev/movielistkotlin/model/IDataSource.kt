package bez.dev.movielistkotlin.model

import io.reactivex.Single

interface IDataSource {
    fun fetchMoviesData(): Single<List<Movie>>

}