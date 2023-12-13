package com.example.testtask.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.testtask.databinding.ItemPhotoBinding
import com.example.testtask.domain.model.ImageOut

class ImageListAdapter(
    private val itemClickListener: RecyclerViewItemClickListener
) : PagingDataAdapter<ImageOut, ImageListHolder>(ImageListDiffCallback()) {

    override fun onBindViewHolder(holder: ImageListHolder, position: Int) {
        val item = getItem(position) ?: return
        item.let {
            holder.onBind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = ItemPhotoBinding.inflate(inflater)
        return ImageListHolder(item, itemClickListener)
    }

    override fun onBindViewHolder(holder: ImageListHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val item = payloads[0] as ImageOut
            holder.onBind(item)
        }

    }
}