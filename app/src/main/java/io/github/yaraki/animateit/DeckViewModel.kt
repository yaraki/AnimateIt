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

package io.github.yaraki.animateit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.yaraki.animateit.slide.Deck
import io.github.yaraki.animateit.slide.Page

class DeckViewModel : ViewModel() {

    private var position = 0

    private val _slide = MutableLiveData<Page>(Deck.pages[position])
    val pages: LiveData<Page>
        get() = _slide

    fun showNext(): Boolean {
        if (position + 1 >= Deck.pages.size) {
            return false
        }
        position++
        _slide.value = Deck.pages[position]
        return true
    }

    fun showPrevious(): Boolean {
        if (position == 0) {
            return false
        }
        position--
        _slide.value = Deck.pages[position]
        return true
    }
}
