package com.example.testtask.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.databinding.ItemCommentBinding
import com.example.testtask.domain.model.CommentOut
import com.example.testtask.domain.model.ImageOut

class CommentsAdapter(
    private val itemLongClickListener: (CommentOut) -> Unit
): RecyclerView.Adapter<CommentsHolder>() {

    private val listCommentItem: MutableList<CommentOut> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsHolder {
        val item = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsHolder(item, itemLongClickListener)
    }

    override fun getItemCount(): Int = listCommentItem.size

    override fun onBindViewHolder(holder: CommentsHolder, position: Int) {
        val item = listCommentItem[position]
        holder.onBind(item)
    }
}