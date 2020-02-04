package bez.dev.movielistkotlin.model

import androidx.room.*
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieList: List<Movie>): Single<List<Long>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()

    @Query("SELECT * FROM movie_table ORDER BY releaseYear DESC")
    fun getAllMoviesByYear(): Single<List<Movie>>
}

