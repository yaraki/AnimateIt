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

package io.github.yaraki.animateit.deck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.transition.Fade
import io.github.yaraki.animateit.R

class TitleFragment : PageFragment() {

    companion object : Page {
        override fun create() = TitleFragment()
    }

    private val texts = listOf("Animate It", "動かす")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fade = Fade()
        enterTransition = fade
        exitTransition = fade
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title: TextView = view.findViewById(R.id.title)
        val spring = SpringAnimation(title, FlingAnimation.ROTATION).apply {
            spring = SpringForce(0f).apply {
                stiffness = SpringForce.STIFFNESS_VERY_LOW
                dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
            }
        }
        var currentVelocity = 0f
        spring.addUpdateListener { _, _, velocity ->
            currentVelocity = velocity
        }
        var count = 0
        title.setOnClickListener {
            spring.setStartVelocity(currentVelocity + 3000f)
            spring.start()
            title.text = texts[count++ % texts.size]
        }
    }
}
