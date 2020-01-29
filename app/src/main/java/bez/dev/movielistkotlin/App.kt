package bez.dev.movielistkotlin

import android.app.Application
import android.content.Context
import bez.dev.movielistkotlin.model.MovieDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        database = MovieDatabase.getInstance(appContext)

    }

    companion object {
        lateinit var appContext: Context
        lateinit var database: MovieDatabase
    }


}
