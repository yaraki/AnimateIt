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
import androidx.core.content.res.ResourcesCompat
import io.github.yaraki.animateit.R
import io.github.yaraki.animateit.databinding.PageViewOverlayBinding
import io.github.yaraki.animateit.deck.Page
import io.github.yaraki.animateit.deck.PageFragment

class ViewOverlayFragment : PageFragment() {

    companion object : Page {
        override fun create() =
            ViewOverlayFragment()
    }

    private lateinit var binding: PageViewOverlayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageViewOverlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.web.settings.run {
            javaScriptEnabled = true
            allowContentAccess = true
            setAppCacheEnabled(true)
        }
        binding.web.loadUrl("file:///android_asset/view_overlay.html")

        ResourcesCompat.getDrawable(resources, R.mipmap.ic_launcher, null)?.let { drawable ->
            drawable.setBounds(100, 100, 400, 400)
            binding.box.overlay.add(drawable)
        }
    }
}
