package com.example.testtask.ui.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.domain.model.CommentOut
import com.example.testtask.domain.repositories.CommentRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val commentRepository: CommentRepository
): ViewModel(){

    private val _commentLiveData = MutableLiveData<CommentOut>()
    val commentLiveData: LiveData<CommentOut> get() = _commentLiveData


}