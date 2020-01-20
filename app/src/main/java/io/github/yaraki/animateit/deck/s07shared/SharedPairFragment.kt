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

package io.github.yaraki.animateit.deck.s07shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commitNow
import io.github.yaraki.animateit.R
import io.github.yaraki.animateit.databinding.PageSharedPairBinding
import io.github.yaraki.animateit.deck.Page
import io.github.yaraki.animateit.deck.PageFragment

class SharedPairFragment : PageFragment() {

    companion object : Page {
        override fun create() = SharedPairFragment()
    }

    private lateinit var binding: PageSharedPairBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageSharedPairBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.commitNow {
            replace(R.id.first, FirstFragment().apply {
                arguments = Bundle().apply { putBoolean(FirstFragment.ARG_NAV, false) }
            })
            replace(R.id.second, SecondFragment().apply {
                arguments = SecondFragmentArgs(R.drawable.cheese_1).toBundle()
            })
        }
    }
}
