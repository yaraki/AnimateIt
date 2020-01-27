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
import io.github.yaraki.animateit.databinding.PageCodeBinding

class CodeFragment : PageFragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_PATH = "path"

        private fun codePage(path: String, title: String? = null): Page {
            return object : Page {
                override fun create() = CodeFragment().apply {
                    arguments = Bundle().apply {
                        if (title != null) {
                            putString(ARG_TITLE, title)
                        }
                        putString(ARG_PATH, path)
                    }
                }
            }
        }

        val DISSOLVE = codePage("dissolve.html")
        val DISSOLVE_USAGE = codePage("dissolve_usage.html", "Dissolve")
        val STAGGER = codePage("stagger.html")
        val MIRROR_VIEW = codePage("mirror_view.html")
        val SHARED_FADE = codePage("shared_fade.html")
    }

    private lateinit var binding: PageCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.web.settings.run {
            javaScriptEnabled = true
            allowContentAccess = true
            setAppCacheEnabled(true)
        }
        val title = arguments?.getString(ARG_TITLE)
        if (title == null) {
            binding.title.visibility = View.GONE
        } else {
            binding.title.visibility = View.VISIBLE
            binding.title.text = title
        }
        arguments?.getString(ARG_PATH)?.let { path ->
            binding.web.loadUrl("file:///android_asset/$path")
        }
    }
}
