package com.parallelspace.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parallelspace.app.business_ability.clone_create.dto.CloneInfoDto
import com.parallelspace.app.databinding.ItemCloneBinding

class CloneListAdapter(
    private val onCloneClick: (CloneInfoDto) -> Unit,
    private val onCloneLongClick: (CloneInfoDto) -> Unit
) : ListAdapter<CloneInfoDto, CloneListAdapter.CloneViewHolder>(CloneDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloneViewHolder {
        val binding = ItemCloneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CloneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CloneViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CloneViewHolder(
        private val binding: ItemCloneBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clone: CloneInfoDto) {
            binding.textCloneName.text = clone.cloneName
            binding.textPackageName.text = clone.packageName
            binding.textStatus.text = when (clone.status) {
                1 -> "Running"
                2 -> "Stopped"
                3 -> "Creating"
                4 -> "Deleting"
                else -> "Error"
            }
            binding.root.setOnClickListener { onCloneClick(clone) }
            binding.root.setOnLongClickListener {
                onCloneLongClick(clone)
                true
            }
        }
    }

    class CloneDiffCallback : DiffUtil.ItemCallback<CloneInfoDto>() {
        override fun areItemsTheSame(oldItem: CloneInfoDto, newItem: CloneInfoDto): Boolean {
            return oldItem.cloneId == newItem.cloneId
        }

        override fun areContentsTheSame(oldItem: CloneInfoDto, newItem: CloneInfoDto): Boolean {
            return oldItem == newItem
        }
    }
}
