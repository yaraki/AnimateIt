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

package io.github.yaraki.animateit.deck.s03basic

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import io.github.yaraki.animateit.R
import io.github.yaraki.animateit.databinding.PageTransitionBinding
import io.github.yaraki.animateit.deck.Page
import io.github.yaraki.animateit.deck.PageFragment
import kotlinx.coroutines.delay

class TransitionFragment : PageFragment() {

    companion object : Page {
        override fun create() = TransitionFragment()
    }

    private lateinit var binding: PageTransitionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageTransitionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.web.settings.run {
            javaScriptEnabled = true
            allowContentAccess = true
            setAppCacheEnabled(true)
        }
        binding.web.loadUrl("file:///android_asset/transition.html")

        val sizeSmall = resources.getDimensionPixelSize(R.dimen.box_small)
        val sizeLarge = resources.getDimensionPixelSize(R.dimen.box_large)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            var count = 0
            while (true) {
                delay(2000)

                TransitionManager.beginDelayedTransition(binding.example)
                binding.box.updateLayoutParams<FrameLayout.LayoutParams> {
                    if (++count % 2 == 0) {
                        gravity = Gravity.START or Gravity.CENTER_VERTICAL
                        width = sizeLarge
                        height = sizeLarge
                    } else {
                        gravity = Gravity.END or Gravity.CENTER_VERTICAL
                        width = sizeSmall
                        height = sizeSmall
                    }
                }
            }
        }
    }

}
