package io.github.yaraki.animateit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.yaraki.animateit.ui.DisplayInfoDialogFragment

private const val TAG_DISPLAY_INFO = "display_info"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        findViewById<Button>(R.id.display_info).setOnClickListener {
            DisplayInfoDialogFragment().show(supportFragmentManager, TAG_DISPLAY_INFO)
        }

        findViewById<FloatingActionButton>(R.id.start).setOnClickListener {
            startActivity(Intent(this, DeckActivity::class.java))
        }
        startActivity(Intent(this, DeckActivity::class.java))
    }
}
