package com.example.testtask.ui.photos

import androidx.recyclerview.widget.DiffUtil
import com.example.testtask.domain.model.ImageOut

class ImageListDiffCallback : DiffUtil.ItemCallback<ImageOut>() {

    override fun areItemsTheSame(oldItem: ImageOut, newItem: ImageOut): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageOut, newItem: ImageOut): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ImageOut, newItem: ImageOut): Any? {
        if (oldItem != newItem) {
            return newItem
        }
        return super.getChangePayload(oldItem, newItem)
    }
}