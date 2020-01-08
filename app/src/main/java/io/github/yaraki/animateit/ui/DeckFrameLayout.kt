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

package io.github.yaraki.animateit.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import io.github.yaraki.animateit.R
import kotlin.math.min

class DeckFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val slideWidth = context.resources.getDimensionPixelSize(R.dimen.page_width)
    private val slideHeight = context.resources.getDimensionPixelSize(R.dimen.page_height)

    private var scale = 1f

    init {
        clipChildren = false
        clipToPadding = false
    }

    override fun measureChild(
        child: View?,
        parentWidthMeasureSpec: Int,
        parentHeightMeasureSpec: Int
    ) {
        super.measureChild(
            child,
            MeasureSpec.makeMeasureSpec(slideWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(slideHeight, MeasureSpec.EXACTLY)
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = width
        val h = height
        if (w != 0 && h != 0) {
            val scaleX = w.toFloat() / slideWidth
            val scaleY = h.toFloat() / slideHeight
            scale = min(scaleX, scaleY)
            children.forEach { v ->
                v.pivotX = 0f
                v.pivotY = 0f
                v.scaleX = scale
                v.scaleY = scale
            }
        }
    }
}
