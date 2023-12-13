package com.example.testtask.ui.comments

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testtask.databinding.ItemCommentBinding
import com.example.testtask.domain.model.CommentOut

class CommentsHolder(
    private val binding: ItemCommentBinding,
    private val itemLongClickListener: (CommentOut) -> Unit
) :ViewHolder(binding.root) {

    fun onBind(item: CommentOut) {
        setCommentInfo(item)
        setClick(item)
    }

    private fun setClick(item: CommentOut) {
        binding.root.setOnLongClickListener {
            itemLongClickListener(item)
            true
        }
    }

    private fun setCommentInfo(item: CommentOut) {
        binding.commentDateTextView.text = item.date
        binding.commentTextView.text = item.text
    }
}