/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yaraki.animateit

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import androidx.transition.Transition
import io.github.yaraki.animateit.deck.Deck
import io.github.yaraki.animateit.deck.PageFragment
import io.github.yaraki.animateit.deck.SlideFade
import io.github.yaraki.animateit.transition.FAST_OUT_LINEAR_IN
import io.github.yaraki.animateit.transition.LINEAR_OUT_SLOW_IN

class DeckActivity : AppCompatActivity() {

    private var position = 5

    private lateinit var forwardExit: Transition
    private lateinit var forwardEnter: Transition
    private lateinit var backwardExit: Transition
    private lateinit var backwardEnter: Transition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deck_activity)

        val distance = resources.getDimensionPixelSize(R.dimen.slide_distance).toFloat()
        forwardExit = SlideFade(Gravity.START, distance).apply {
            interpolator = FAST_OUT_LINEAR_IN
            duration = 100
        }
        forwardEnter = SlideFade(Gravity.START, distance).apply {
            interpolator = LINEAR_OUT_SLOW_IN
            startDelay = 100
            duration = 150
        }
        backwardExit = SlideFade(Gravity.END, distance).apply {
            interpolator = FAST_OUT_LINEAR_IN
            duration = 100
        }
        backwardEnter = SlideFade(Gravity.END, distance).apply {
            interpolator = LINEAR_OUT_SLOW_IN
            startDelay = 100
            duration = 150
        }

        showCurrentPage()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_DOWN,
            KeyEvent.KEYCODE_DPAD_RIGHT,
            KeyEvent.KEYCODE_VOLUME_DOWN,
            KeyEvent.KEYCODE_PAGE_DOWN,
            KeyEvent.KEYCODE_J -> {
                showNext()
                true
            }
            KeyEvent.KEYCODE_DPAD_UP,
            KeyEvent.KEYCODE_DPAD_LEFT,
            KeyEvent.KEYCODE_VOLUME_UP,
            KeyEvent.KEYCODE_PAGE_UP,
            KeyEvent.KEYCODE_K -> {
                showPrevious()
                true
            }
            KeyEvent.KEYCODE_MOVE_HOME -> {
                position = 0
                showCurrentPage()
                true
            }
            KeyEvent.KEYCODE_MOVE_END -> {
                position = Deck.pages.size - 1
                showCurrentPage()
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUi()
        }
    }

    private fun showCurrentPage() {
        val page = Deck.pages[position]
        supportFragmentManager.commitNow {
            replace(R.id.container, page.create())
        }
    }

    private fun showNext() {
        val current = supportFragmentManager.findFragmentById(R.id.container) as? PageFragment
        if (current != null && current.showNextStep()) {
            return
        }
        if (position + 1 >= Deck.pages.size) {
            return
        }
        val page = Deck.pages[++position]
        val fragment = page.create()
        current?.exitTransition = forwardExit
        fragment.enterTransition = forwardEnter
        supportFragmentManager.commitNow {
            replace(R.id.container, fragment)
        }
    }

    private fun showPrevious() {
        val current = supportFragmentManager.findFragmentById(R.id.container) as? PageFragment
        if (current != null && current.showPreviousStep()) {
            return
        }
        if (position == 0) {
            return
        }
        val page = Deck.pages[--position]
        val fragment = page.create()
        current?.exitTransition = backwardExit
        fragment.enterTransition = backwardEnter
        supportFragmentManager.commitNow {
            replace(R.id.container, fragment)
        }
    }

    private fun hideSystemUi() {
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}
