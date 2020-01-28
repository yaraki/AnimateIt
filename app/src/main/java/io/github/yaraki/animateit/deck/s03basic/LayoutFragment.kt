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
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionManager
import io.github.yaraki.animateit.databinding.PageLayoutBinding
import io.github.yaraki.animateit.deck.Page
import io.github.yaraki.animateit.deck.PageFragment
import kotlinx.coroutines.delay

class LayoutFragment : PageFragment() {

    companion object : Page {
        override fun create() = LayoutFragment()
    }

    private lateinit var binding: PageLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val displayMetrics = resources.displayMetrics
        val d100 =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, displayMetrics).toInt()
        val d200 =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, displayMetrics).toInt()
        val operations = listOf({
            binding.box1.updateLayoutParams<LinearLayout.LayoutParams> {
                height = d100
            }
        }, {
            binding.box1.updateLayoutParams<LinearLayout.LayoutParams> {
                height = d200
            }
        }, {
            binding.box2.visibility = View.GONE
        }, {
            binding.box2.visibility = View.VISIBLE
        }, {
            binding.box3.updateLayoutParams<LinearLayout.LayoutParams> {
                weight = 9f
            }
        }, {
            binding.box3.updateLayoutParams<LinearLayout.LayoutParams> {
                weight = 1f
            }
        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            var count = 0
            while (true) {
                delay(1000)
                TransitionManager.beginDelayedTransition(binding.example)
                operations[count++ % operations.size].invoke()
            }
        }
    }
}
