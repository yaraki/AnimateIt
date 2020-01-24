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

package io.github.yaraki.animateit.deck.s06oscillation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.yaraki.animateit.databinding.PageOscillationListBinding
import io.github.yaraki.animateit.deck.Cheese
import io.github.yaraki.animateit.deck.Page
import io.github.yaraki.animateit.deck.PageFragment

class OscillationListFragment : PageFragment() {

    companion object : Page {
        override fun create() =
            OscillationListFragment()
    }

    private lateinit var binding: PageOscillationListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageOscillationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter =
            CheeseBoardAdapter()
        binding.list.adapter = adapter
        binding.list.addOnScrollListener(adapter.onScrollListener)
        binding.list.edgeEffectFactory = adapter.edgeEffectFactory

        adapter.submitList(Cheese.ALL.filter { it.name.length == 9 }.take(15))
    }
}
