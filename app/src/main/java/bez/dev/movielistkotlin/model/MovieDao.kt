package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY releaseYear DESC")
    fun getAllMoviesByYear(): LiveData<MutableList<Movie>>
}

