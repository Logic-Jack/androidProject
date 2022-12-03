package com.teamlink.teamactivityviewer.ui.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamlink.teamactivityviewer.databinding.EventdataListItemBinding
import com.teamlink.teamactivityviewer.ui.data.model.EventData

class EventListAdapter(private val onClubClicked: (EventData) -> Unit)
    : ListAdapter<EventData, EventListAdapter.EventDataViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventDataViewHolder {
        return EventDataViewHolder(
            EventdataListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: EventDataViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onClubClicked(current)
        }
        holder.bind(current)
    }

    class EventDataViewHolder(private var binding: EventdataListItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: EventData){
            binding.itemName.text = item.Name
            binding.itemDesc.text = item.Description
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<EventData>() {
            override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: EventData, newItem: EventData): Boolean {
                return oldItem.Id == newItem.Id
            }
        }
    }
}