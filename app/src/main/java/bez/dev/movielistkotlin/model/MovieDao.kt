package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieList: MutableList<Movie>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()

    @Query("SELECT * FROM movie_table ORDER BY releaseYear DESC")
    fun getAllMoviesByYear(): LiveData<MutableList<Movie>>
}

