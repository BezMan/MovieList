package bez.dev.movielistkotlin.model

import androidx.lifecycle.LiveData
import bez.dev.movielistkotlin.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MockNoteRepository {

    private val movieDatabase: MovieDatabase = App.database
    private val movieDao: MovieDao = movieDatabase.movieDao()
    private val allNotes: LiveData<List<Movie>>
    private val repoScope = CoroutineScope(Dispatchers.Default)


    init {
        allNotes = movieDao.getAllMoviesByYear()
    }


//    fun fetchMoviesData(): LiveData<ArrayList<Movie>> {
//        return allNotes
//    }
//
//    fun getMoviesList(): LiveData<ArrayList<Movie>> {
//return allNotes
//    }

}