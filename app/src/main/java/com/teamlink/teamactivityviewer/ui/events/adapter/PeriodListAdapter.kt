package com.teamlink.teamactivityviewer.ui.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamlink.teamactivityviewer.databinding.EventdataListItemBinding
import com.teamlink.teamactivityviewer.databinding.PeriodListItemBinding
import com.teamlink.teamactivityviewer.ui.data.model.CustomPeriod

class PeriodListAdapter: ListAdapter<CustomPeriod, PeriodListAdapter.CustomPeriodViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomPeriodViewHolder {
        return CustomPeriodViewHolder(
            PeriodListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: CustomPeriodViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class CustomPeriodViewHolder(private var binding: PeriodListItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: CustomPeriod){
            binding.itemDate.text = "${item.StartDate} - ${item.EndDate}"
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CustomPeriod>() {
            override fun areItemsTheSame(oldItem: CustomPeriod, newItem: CustomPeriod): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: CustomPeriod, newItem: CustomPeriod): Boolean {
                return oldItem.Id == newItem.Id
            }
        }
    }
}