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
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.transition.Fade
import androidx.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import io.github.yaraki.animateit.R
import io.github.yaraki.animateit.deck.DeckViewModel
import io.github.yaraki.animateit.transition.FAST_OUT_LINEAR_IN
import io.github.yaraki.animateit.transition.LARGE_COLLAPSE_DURATION
import io.github.yaraki.animateit.transition.LARGE_EXPAND_DURATION
import io.github.yaraki.animateit.transition.LINEAR_OUT_SLOW_IN

class CheeseCardFragment : Fragment() {

    companion object {
        const val ARG_NAV = "nav"
    }

    private val viewModel: CheeseCardViewModel by viewModels()

    private val deckViewModel: DeckViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Fade(Fade.OUT).apply {
            duration = LARGE_EXPAND_DURATION / 2
            interpolator = FAST_OUT_LINEAR_IN
        }
        reenterTransition = Fade(Fade.IN).apply {
            duration = LARGE_COLLAPSE_DURATION / 2
            startDelay = LARGE_COLLAPSE_DURATION / 2
            interpolator = LINEAR_OUT_SLOW_IN
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cheese_card_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val content: FrameLayout = view.findViewById(R.id.content)
        val card: MaterialCardView = view.findViewById(R.id.card)
        val cardContent: ConstraintLayout = view.findViewById(R.id.card_content)
        val image: ImageView = view.findViewById(R.id.image)
        val name: TextView = view.findViewById(R.id.name)
        val mirror: MirrorView = view.findViewById(R.id.article_mirror)

        ViewCompat.setOnApplyWindowInsetsListener(view.parent as View) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            toolbar.updateLayoutParams<AppBarLayout.LayoutParams> {
                topMargin = systemBars.top
            }
            content.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }

        ViewCompat.setTransitionName(card, "card")
        ViewCompat.setTransitionName(cardContent, "card_content")
        ViewCompat.setTransitionName(mirror, "article")
        ViewGroupCompat.setTransitionGroup(cardContent, true)

        viewModel.cheese.observe(viewLifecycleOwner) { cheese ->
            name.text = cheese.name
            image.setImageResource(cheese.image)
        }

        val nav = arguments?.getBoolean(ARG_NAV, true) ?: true

        deckViewModel.slowContainer.observe(viewLifecycleOwner) { slow ->
            if (slow) {
                (exitTransition as? Transition)?.duration = (LARGE_EXPAND_DURATION / 2) * 10
                (reenterTransition as? Transition)?.run {
                    duration = (LARGE_COLLAPSE_DURATION / 2) * 10
                    startDelay = (LARGE_COLLAPSE_DURATION / 2) * 10
                }
            } else {
                (exitTransition as? Transition)?.duration = LARGE_EXPAND_DURATION / 2
                (reenterTransition as? Transition)?.run {
                    duration = LARGE_COLLAPSE_DURATION / 2
                    startDelay = LARGE_COLLAPSE_DURATION / 2
                }
            }
        }

        card.setOnClickListener { v ->
            if (nav) {
                v.findNavController().navigate(
                    CheeseCardFragmentDirections.actionArticle(),
                    FragmentNavigatorExtras(
                        card to CheeseArticleFragment.TRANSITION_NAME_BACKGROUND,
                        cardContent to CheeseArticleFragment.TRANSITION_NAME_CARD_CONTENT,
                        mirror to CheeseArticleFragment.TRANSITION_NAME_ARTICLE_CONTENT
                    )
                )
            }
        }
    }
}
