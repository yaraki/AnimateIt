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

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewGroupCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import io.github.yaraki.animateit.R

internal class CheeseListAdapter : ListAdapter<Cheese, CheeseViewHolder>(Cheese.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
        return CheeseViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

internal class CheeseViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater
        .from(parent.context)
        .inflate(R.layout.cheese_list_item, parent, false)
) {

    private val image: ImageView = itemView.findViewById(R.id.image)
    private val name: TextView = itemView.findViewById(R.id.name)

    init {
        ViewGroupCompat.setTransitionGroup(itemView as ViewGroup, true)
    }

    fun bind(cheese: Cheese) {
        Glide.with(image).load(cheese.image).transform(CircleCrop()).into(image)
        name.text = cheese.name
    }
}
