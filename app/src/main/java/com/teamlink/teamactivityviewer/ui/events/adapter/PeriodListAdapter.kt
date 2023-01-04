package com.teamlink.teamactivityviewer.ui.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamlink.teamactivityviewer.databinding.EventdataListItemBinding
import com.teamlink.teamactivityviewer.databinding.PeriodListItemBinding
import com.teamlink.teamactivityviewer.room.entity.CustomPeriodEntity
import com.teamlink.teamactivityviewer.ui.data.model.CustomPeriod

class PeriodListAdapter
    : ListAdapter<CustomPeriodEntity, PeriodListAdapter.CustomPeriodViewHolder>(DiffCallback) {

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

        fun bind(item: CustomPeriodEntity){
            binding.startRow.text = "Start: "
            binding.endRow.text = "End: "
            binding.itemDateStart.text = item.StartDate
            binding.itemDateEnd.text = item.EndDate
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CustomPeriodEntity>() {
            override fun areItemsTheSame(oldItem: CustomPeriodEntity, newItem: CustomPeriodEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: CustomPeriodEntity, newItem: CustomPeriodEntity): Boolean {
                return oldItem.Id == newItem.Id
            }
        }
    }
}