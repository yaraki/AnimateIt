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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.Transition
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.github.yaraki.animateit.R
import io.github.yaraki.animateit.deck.DeckViewModel
import io.github.yaraki.animateit.transition.*

class CheeseArticleFragment : Fragment() {

    companion object {
        const val TRANSITION_NAME_BACKGROUND = "background"
        const val TRANSITION_NAME_CARD_CONTENT = "card_content"
        const val TRANSITION_NAME_ARTICLE_CONTENT = "article_content"
    }

    private val viewModel: CheeseArticleViewModel by viewModels()
    private val deckViewModel: DeckViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // These are the shared element transitions.
        sharedElementEnterTransition =
            createSharedElementTransition(LARGE_EXPAND_DURATION, R.id.article_mirror)
        sharedElementReturnTransition =
            createSharedElementTransition(LARGE_COLLAPSE_DURATION, R.id.card_mirror)

        viewModel.cheeseId = 346L
    }

    private fun createSharedElementTransition(duration: Long, @IdRes noTransform: Int): Transition {
        return transitionTogether {
            this.duration = duration
            interpolator = FAST_OUT_SLOW_IN
            this += SharedFade()
            this += ChangeBounds()
            this += ChangeTransform()
                // The content is already transformed along with the parent. Exclude it.
                .excludeTarget(noTransform, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cheese_article_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image)
        val scroll: NestedScrollView = view.findViewById(R.id.scroll)
        val content: LinearLayout = view.findViewById(R.id.content)

        val background: FrameLayout = view.findViewById(R.id.background)
        val coordinator: CoordinatorLayout = view.findViewById(R.id.coordinator)
        val mirror: MirrorView = view.findViewById(R.id.card_mirror)

        ViewCompat.setTransitionName(background, TRANSITION_NAME_BACKGROUND)
        ViewCompat.setTransitionName(coordinator, TRANSITION_NAME_ARTICLE_CONTENT)
        ViewCompat.setTransitionName(mirror, TRANSITION_NAME_CARD_CONTENT)
        ViewGroupCompat.setTransitionGroup(coordinator, true)

        // Adjust the edge-to-edge display.
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            toolbar.updateLayoutParams<CollapsingToolbarLayout.LayoutParams> {
                topMargin = systemBars.top
            }
            // The collapsed app bar gets taller by the toolbar's top margin. The CoordinatorLayout
            // has to have a bottom margin of the same amount so that the scrolling content is
            // completely visible.
            scroll.updateLayoutParams<CoordinatorLayout.LayoutParams> {
                bottomMargin = systemBars.top
            }
            content.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }

        deckViewModel.slowContainer.observe(viewLifecycleOwner) { slow ->
            if (slow) {
                sharedElementEnterTransition =
                    createSharedElementTransition(LARGE_EXPAND_DURATION * 10, R.id.article_mirror)
                sharedElementReturnTransition =
                    createSharedElementTransition(LARGE_COLLAPSE_DURATION * 10, R.id.card_mirror)
            } else {
                sharedElementEnterTransition =
                    createSharedElementTransition(LARGE_EXPAND_DURATION, R.id.article_mirror)
                sharedElementReturnTransition =
                    createSharedElementTransition(LARGE_COLLAPSE_DURATION, R.id.card_mirror)
            }
        }

        viewModel.cheese.observe(viewLifecycleOwner) { cheese ->
            if (cheese != null) {
                name.text = cheese.name
                image.setImageResource(cheese.image)
            }
        }

        toolbar.setNavigationOnClickListener { v ->
            v.findNavController().popBackStack()
        }
    }
}
