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
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import io.github.yaraki.animateit.R
import io.github.yaraki.animateit.databinding.PageStaggerBinding

class StaggerFragment : PageFragment() {

    companion object : Page {
        override fun create() = StaggerFragment()
    }

    private lateinit var binding: PageStaggerBinding
    private val viewModel: CheeseListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageStaggerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = CheeseListAdapter()
        binding.list.adapter = adapter
        val stagger = Stagger()
        val originalItemAnimator = binding.list.itemAnimator

        val listener = object: TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                binding.list.itemAnimator = originalItemAnimator
            }
        }
        stagger.addListener(listener)
        viewModel.cheeses.observe(viewLifecycleOwner) { cheeses ->
            binding.list.itemAnimator = null
            adapter.submitList(cheeses) {
                TransitionManager.beginDelayedTransition(binding.list, stagger)
            }
        }
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_refresh -> {
                    stagger.duration = 125
                    viewModel.empty()
                    viewModel.refresh()
                    true
                }
                R.id.action_refresh_slow -> {
                    stagger.duration = 1250
                    viewModel.empty()
                    viewModel.refresh()
                    true
                }
                else -> false
            }
        }
    }
}
