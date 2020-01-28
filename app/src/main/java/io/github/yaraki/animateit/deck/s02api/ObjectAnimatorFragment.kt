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

package io.github.yaraki.animateit.deck.s02api

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import io.github.yaraki.animateit.databinding.PageObjectAnimatorBinding
import io.github.yaraki.animateit.deck.Page
import io.github.yaraki.animateit.deck.PageFragment

class ObjectAnimatorFragment : PageFragment() {

    companion object : Page {
        override fun create() = ObjectAnimatorFragment()
    }

    private lateinit var binding: PageObjectAnimatorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageObjectAnimatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObjectAnimator.ofFloat(binding.demoRotation, View.ROTATION, 0f, 360f).apply {
            interpolator = LinearInterpolator()
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
        }.start()
        ObjectAnimator.ofInt(binding.demoSeek, "progress", 0, 100).apply {
            interpolator = LinearInterpolator()
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
        }.start()
        ObjectAnimator.ofPropertyValuesHolder(
            binding.demoMultiple,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 0.6f, 1.2f, 0.6f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f, 0.6f, 1.2f)
        ).apply {
            interpolator = LinearInterpolator()
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
        }.start()
    }
}
