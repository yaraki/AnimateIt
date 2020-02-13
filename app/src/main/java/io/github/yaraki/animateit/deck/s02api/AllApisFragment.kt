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

package io.github.yaraki.animateit.deck.s02api

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import io.github.yaraki.animateit.databinding.PageAllApisBinding
import io.github.yaraki.animateit.deck.Page

class AllApisFragment : Fragment() {

    companion object : Page {
        override fun create() =
            AllApisFragment()
    }

    private lateinit var binding: PageAllApisBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageAllApisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.labelAnimation.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.scrim.visibility = View.VISIBLE
            binding.frameAnimation.visibility = View.VISIBLE
        }
        binding.forgetAnimation.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.scrim.visibility = View.INVISIBLE
            binding.frameAnimation.visibility = View.INVISIBLE
            binding.labelAnimation.paintFlags =
                binding.labelAnimation.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.labelLt.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.scrim.visibility = View.VISIBLE
            binding.frameLt.visibility = View.VISIBLE
        }
        binding.forgetLt.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.scrim.visibility = View.INVISIBLE
            binding.frameLt.visibility = View.INVISIBLE
            binding.labelLt.paintFlags =
                binding.labelLt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.labelVpa.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.scrim.visibility = View.VISIBLE
            binding.frameVpa.visibility = View.VISIBLE
        }
        binding.dontOveruseVpa.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root)
            binding.scrim.visibility = View.INVISIBLE
            binding.frameVpa.visibility = View.INVISIBLE
            binding.labelVpa.animate().alpha(0.5f)
        }
        binding.labelMl.setOnClickListener {
            binding.labelMl.text = "(MotionLayout)"
        }
    }
}
