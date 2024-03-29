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

package io.github.yaraki.animateit.deck.s09container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import io.github.yaraki.animateit.R
import io.github.yaraki.animateit.databinding.PageContainerBinding
import io.github.yaraki.animateit.databinding.PageContainerUsageBinding
import io.github.yaraki.animateit.databinding.PageContainerUsageBindingImpl
import io.github.yaraki.animateit.deck.DeckViewModel
import io.github.yaraki.animateit.deck.Page

class ContainerUsageFragment : Fragment() {

    companion object : Page {
        override fun create() = ContainerUsageFragment()
    }

    private val deckViewModel: DeckViewModel by activityViewModels()

    private lateinit var binding: PageContainerUsageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PageContainerUsageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            binding.navHost.findNavController().popBackStack(R.id.cheeseCardFragment, false)
        }
        deckViewModel.slowContainer.observe(viewLifecycleOwner) { slow ->
            binding.slow.isChecked = slow
        }
        binding.slow.setOnCheckedChangeListener { _, isChecked ->
            deckViewModel.setSlowContainer(isChecked)
        }

        binding.web.settings.run {
            javaScriptEnabled = true
            allowContentAccess = true
        }
        binding.web.loadUrl("file:///android_asset/container_transform_usage.html")
    }
}
