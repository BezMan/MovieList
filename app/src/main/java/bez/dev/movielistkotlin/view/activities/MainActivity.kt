package bez.dev.movielistkotlin.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bez.dev.movielistkotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBarChoose.setTitle(R.string.app_name)
        setActionBar(actionBarChoose)
    }
}
