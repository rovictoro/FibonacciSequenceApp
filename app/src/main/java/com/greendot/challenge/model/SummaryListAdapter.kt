package com.greendot.challenge.model

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.greendot.challenge.R


class SummaryListAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<SummaryListAdapter.SummaryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var summaries = emptyList<Summary>() // Cached copy of words

    inner class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberItemView: TextView = itemView.findViewById(R.id.summary_number)
        val timeItemView: TextView = itemView.findViewById(R.id.summary_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val itemView = inflater.inflate(R.layout.summary_item, parent, false)
        return SummaryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val current = summaries[position]
        holder.numberItemView.text = current.number
        holder.timeItemView.text = current.time
    }

    internal fun setSummaries(summaries: List<Summary>) {
        this.summaries = summaries
        notifyDataSetChanged()
    }

    override fun getItemCount() = summaries.size
}


