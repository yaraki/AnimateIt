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

import io.github.yaraki.animateit.deck.s01intro.TitleFragment
import io.github.yaraki.animateit.deck.s02api.AllApisFragment
import io.github.yaraki.animateit.deck.s02api.MaterialIoFragment
import io.github.yaraki.animateit.deck.s02api.ObjectAnimatorFragment
import io.github.yaraki.animateit.deck.s02api.ViewAnimationFragment
import io.github.yaraki.animateit.deck.s04dissolve.DissolveCrossFadeFragment
import io.github.yaraki.animateit.deck.s04dissolve.ViewOverlayFragment
import io.github.yaraki.animateit.deck.s05fadethrough.DissolveFadeThroughFragment
import io.github.yaraki.animateit.deck.s05fadethrough.FadeThroughFragment
import io.github.yaraki.animateit.deck.s06stagger.StaggerFragment
import io.github.yaraki.animateit.deck.s06stagger.StaggerUsageFragment
import io.github.yaraki.animateit.deck.s07oscillation.OscillationFragment
import io.github.yaraki.animateit.deck.s07oscillation.OscillationListFragment
import io.github.yaraki.animateit.deck.s07oscillation.OscillationSingleFragment
import io.github.yaraki.animateit.deck.s08shared.SharedCodeFragment
import io.github.yaraki.animateit.deck.s08shared.SharedFragment
import io.github.yaraki.animateit.deck.s08shared.SharedPairFragment
import io.github.yaraki.animateit.deck.s08shared.SharedSummaryFragment
import io.github.yaraki.animateit.deck.s09container.ContainerFragment
import io.github.yaraki.animateit.deck.s09container.ContainerPairFragment
import io.github.yaraki.animateit.deck.s09container.ContainerProgressFragment
import io.github.yaraki.animateit.deck.s10outro.ResourcesFragment

object Deck {

    val pages = listOf(

        // intro
        TitleFragment,

        // api
        AllApisFragment,
        ViewAnimationFragment,
        ObjectAnimatorFragment,
        MaterialIoFragment,

        // dissolve
        DissolveCrossFadeFragment,
        ViewOverlayFragment,
        CodeFragment.DISSOLVE,
        CodeFragment.DISSOLVE_USAGE,

        // fade-through
        DissolveFadeThroughFragment,
        FadeThroughFragment,

        // stagger
        StaggerFragment,
        CodeFragment.STAGGER,
        StaggerUsageFragment,

        // oscillation
        OscillationFragment,
        OscillationSingleFragment,
        OscillationListFragment,

        // shared
        SharedFragment,
        SharedPairFragment,
        SharedCodeFragment,
        SharedSummaryFragment,

        // container
        ContainerPairFragment,
        ContainerFragment,
        ContainerProgressFragment,
        CodeFragment.MIRROR_VIEW,
        CodeFragment.SHARED_FADE,
        ContainerFragment,

        // outro
        ResourcesFragment
    )
}
