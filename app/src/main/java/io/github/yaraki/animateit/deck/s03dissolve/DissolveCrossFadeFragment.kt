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

package io.github.yaraki.animateit.deck.s03dissolve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import io.github.yaraki.animateit.databinding.PageDissolveCrossFadeBinding
import io.github.yaraki.animateit.deck.Cheese
import io.github.yaraki.animateit.deck.Page
import io.github.yaraki.animateit.deck.PageFragment
import io.github.yaraki.animateit.transition.Dissolve
import kotlinx.coroutines.delay

class DissolveCrossFadeFragment : PageFragment() {

    companion object : Page {
        override fun create() =
            DissolveCrossFadeFragment()
    }

    private lateinit var binding: PageDissolveCrossFadeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageDissolveCrossFadeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transition = TransitionSet().apply {
            ordering = TransitionSet.ORDERING_TOGETHER
            duration = 2500
            interpolator = FastOutSlowInInterpolator()
            addTransition(Dissolve().apply {
                addTarget(binding.imageDissolve)
            })
            addTransition(Fade().apply {
                addTarget(binding.imageCrossFade1)
                addTarget(binding.imageCrossFade2)
            })
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            var count = 0
            while (true) {
                TransitionManager.beginDelayedTransition(binding.root as ViewGroup, transition)
                val drawableId = Cheese.IMAGES[count % Cheese.IMAGES.size]
                if (count % 2 == 0) {
                    binding.imageCrossFade1.run {
                        setImageResource(drawableId)
                        visibility = View.VISIBLE
                    }
                    binding.imageCrossFade2.visibility = View.INVISIBLE
                } else {
                    binding.imageCrossFade1.visibility = View.INVISIBLE
                    binding.imageCrossFade2.run {
                        setImageResource(drawableId)
                        visibility = View.VISIBLE
                    }
                }
                binding.drawableId = drawableId
                count++
                if (count > 1) {
                    delay(3000)
                } else {
                    delay(1000)
                }
            }
        }
    }
}
