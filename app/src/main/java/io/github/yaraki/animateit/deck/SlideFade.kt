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

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.transition.Fade
import androidx.transition.TransitionValues

class SlideFade(
    private val edge: Int,
    private val distance: Float
) : Fade(IN or OUT) {

    override fun onDisappear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        return addSlide(
            super.onDisappear(sceneRoot, view, startValues, endValues),
            view,
            0f,
            if (edge == Gravity.START) -distance else distance
        )
    }

    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        return addSlide(
            super.onAppear(sceneRoot, view, startValues, endValues),
            view,
            if (edge == Gravity.START) distance else -distance,
            0f
        )
    }

    private fun addSlide(
        fadeAnimator: Animator?,
        view: View?,
        startX: Float,
        endX: Float
    ): Animator? {
        if (fadeAnimator == null || view == null) return null
        return AnimatorSet().apply {
            playTogether(
                fadeAnimator,
                ObjectAnimator.ofFloat(view, View.TRANSLATION_X, startX, endX)
            )
        }
    }
}
