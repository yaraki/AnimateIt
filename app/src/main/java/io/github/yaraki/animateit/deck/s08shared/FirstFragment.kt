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

package io.github.yaraki.animateit.deck.s08shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.Explode
import io.github.yaraki.animateit.databinding.FirstFragmentBinding
import io.github.yaraki.animateit.deck.Cheese

class FirstFragment : Fragment() {

    companion object {
        const val ARG_NAV = "no_nav"
    }

    private lateinit var binding: FirstFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Explode().apply {
            duration = 150
        }
        reenterTransition = Explode().apply {
            startDelay = 150
            duration = 150
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FirstFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val images =
            listOf(binding.image1, binding.image2, binding.image3, binding.image4, binding.image5)
        val nav = arguments?.getBoolean(ARG_NAV, true) ?: true
        images.forEachIndexed { i, iv ->
            ViewCompat.setTransitionName(iv, "image${i + 1}")
            if (nav) {
                iv.setOnClickListener { v ->
                    v.findNavController().navigate(
                        FirstFragmentDirections.actionFirstToSecond(Cheese.IMAGES[i]),
                        FragmentNavigatorExtras(
                            v to SecondFragment.TRANSITION_NAME_IMAGE
                        )
                    )
                }
            }
        }
    }
}
