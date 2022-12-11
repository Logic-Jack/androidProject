package com.teamlink.teamactivityviewer.ui.clubs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamlink.teamactivityviewer.databinding.ClubdataListItemBinding
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import com.teamlink.teamactivityviewer.ui.data.model.ClubData

class ClubListAdapter(private val onClubClicked: (ClubEntity) -> Unit)
    : ListAdapter<ClubEntity, ClubListAdapter.ClubDataViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubDataViewHolder {
        return ClubDataViewHolder(
            ClubdataListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ClubDataViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onClubClicked(current)
        }
        holder.bind(current)
    }

    class ClubDataViewHolder(private var binding: ClubdataListItemBinding)
        : RecyclerView.ViewHolder(binding.root){

            fun bind(item: ClubEntity){
                binding.itemName.text = item.Name
                binding.itemDesc.text = item.Description
            }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ClubEntity>() {
            override fun areItemsTheSame(oldItem: ClubEntity, newItem: ClubEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ClubEntity, newItem: ClubEntity): Boolean {
                return oldItem.Id == newItem.Id
            }
        }
    }
}