package com.example.testtask.ui.comments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testtask.databinding.FragmentCommentsBinding
import com.example.testtask.DaggerApp
import com.example.testtask.R
import com.example.testtask.domain.model.CommentOut
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CommentsFragment: Fragment() {

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!
    private val textWatcher =
        object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewmodel.livedata.observe(...) { comment ->
//            setImage(comment.url, binding.photoImageView)
//        }

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        val commentsListAdapter = CommentsAdapter(
            itemLongClickListener = { setLongClick(it) }
        )

        binding.commentsRecyclerView.adapter = commentsListAdapter
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.commentsRecyclerView.layoutManager = layoutManager
    }

    private fun setLongClick(commentOut: CommentOut): Boolean {
        showDeleteConfirmationDialog(commentOut)
        return true
    }


    private fun showDeleteConfirmationDialog(commentOut: CommentOut) {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Подтверждение удаления")
//            .setMessage("Вы уверены, что хотите удалить комментарий?")
//            .setPositiveButton("Да") { _, _ ->
//                viewModel.deleteImage(imageOut.id)
//            }
//            .setNegativeButton("Отмена", null)
//            .show()
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .centerCrop()
            .into(image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}