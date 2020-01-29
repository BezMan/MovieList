package bez.dev.movielistkotlin.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bez.dev.movielistkotlin.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startMainActivityDelay()

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()

    }


    private fun startMainActivityDelay() {
        compositeDisposable.add(
            Observable.timer(1, TimeUnit.SECONDS)
                .subscribe {
                    openMainActivity()
                })


    }

    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}