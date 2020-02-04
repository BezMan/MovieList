package bez.dev.movielistkotlin.model

import androidx.room.*
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieList: ArrayList<Movie>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()

    @Query("SELECT * FROM movie_table ORDER BY releaseYear DESC")
    fun getAllMoviesByYear(): Single<ArrayList<Movie>>
}

