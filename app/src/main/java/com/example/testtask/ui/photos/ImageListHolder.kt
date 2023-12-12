package com.example.testtask.ui.photos

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.testtask.databinding.ItemPhotoBinding
import com.example.testtask.domain.model.ImageOut

class ImageListHolder(
    private val binding: ItemPhotoBinding,
    private val itemClickListener: RecyclerViewItemClickListener
) : ViewHolder(binding.root) {

    fun onBind(item: ImageOut) {
        setDate(item)
        setImage(item.url, binding.photoImageView)
        setClick(item)
    }

    private fun setClick(item: ImageOut) {
        itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
        itemView.setOnLongClickListener {
            itemClickListener.onItemLongClick(item)
        }
    }

    private fun setDate(item: ImageOut) {
        binding.dateTextView.text = item.date
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }
}

interface RecyclerViewItemClickListener {
    fun onItemClick(item: ImageOut)
    fun onItemLongClick(item: ImageOut): Boolean
}