package com.example.testtask.ui.photos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testtask.DaggerApp
import com.example.testtask.R
import com.example.testtask.databinding.FragmentListPhotoBinding
import com.example.testtask.di.viewmodel.ViewModelFactory
import com.example.testtask.domain.model.ImageOut
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageListFragment : Fragment(), RecyclerViewItemClickListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: ImageListViewModel by viewModels { factory }
    private var _binding: FragmentListPhotoBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageListAdapter: ImageListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi()

        setupRecyclerView()
        observeImage()
        setupViewModel()
    }

    private fun showDeleteConfirmationDialog(imageOut: ImageOut) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation of deletion")
            .setMessage("Are you sure you want to delete the image?")
            .setPositiveButton("Yes") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.deleteImage(imageOut.id, imageOut)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupRecyclerView() {
        imageListAdapter = ImageListAdapter(this)
        binding.photoRecyclerView.apply {
            imageListAdapter.withLoadStateFooter(ImageListLoadStateAdapter())
            imageListAdapter.addLoadStateListener {
                binding.photoRecyclerView.isVisible = it.refresh != LoadState.Loading
            }
            layoutManager = GridLayoutManager(context, 3)
            adapter = imageListAdapter
        }

    }

    private fun observeImage() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.imageFlow.collect {
                imageListAdapter.submitData(it)
            }
        }
    }

    private fun setupViewModel() {
        viewModel.deleteImageLiveData.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast
                    .makeText(context, "The photo was successfully deleted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast
                    .makeText(context, "The photo has not been deleted", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun updateUi() {
        viewModel.update()
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.visibility = View.VISIBLE
    }

    override fun onItemLongClick(item: ImageOut): Boolean {
        showDeleteConfirmationDialog(item)
        return true
    }

    override fun onItemClick(item: ImageOut) {
        val action = ImageListFragmentDirections.actionNavPhotosToCommentsFragment(item.url)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}