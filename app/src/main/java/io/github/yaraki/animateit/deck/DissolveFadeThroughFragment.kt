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
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import io.github.yaraki.animateit.R
import io.github.yaraki.animateit.databinding.PageDissolveFadeThroughBinding
import io.github.yaraki.animateit.transition.Dissolve
import io.github.yaraki.animateit.transition.fadeThrough
import kotlinx.coroutines.delay

class DissolveFadeThroughFragment : PageFragment() {

    companion object : Page {
        override fun create() = DissolveFadeThroughFragment()
    }

    private lateinit var binding: PageDissolveFadeThroughBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageDissolveFadeThroughBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(binding.content1Icon)
            .load(R.drawable.cheese_2)
            .transform(CircleCrop())
            .into(binding.content1Icon)
        val dissolve = Dissolve().apply {
            addTarget(binding.imageDissolve)
            duration = 2500
        }
        val fadeThrough = fadeThrough().apply {
            duration = 2500
        }
        lifecycleScope.launchWhenStarted {
            var count = 0
            while (true) {
                TransitionManager.beginDelayedTransition(binding.paneDissolve, dissolve)
                binding.imageDissolve.setImageResource(Deck.images[count++ % Deck.images.size])

                TransitionManager.beginDelayedTransition(binding.paneFadeThrough, fadeThrough)
                if (count % 2 == 0) {
                    binding.content1.visibility = View.GONE
                    binding.content2.visibility = View.VISIBLE
                } else {
                    binding.content1.visibility = View.VISIBLE
                    binding.content2.visibility = View.GONE
                }

                if (count > 1) {
                    delay(3000)
                }
            }
        }
    }

}
