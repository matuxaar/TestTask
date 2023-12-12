package com.example.testtask.ui.photos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.DaggerApp
import com.example.testtask.R
import com.example.testtask.databinding.FragmentListPhotoBinding
import com.example.testtask.di.viewmodel.ViewModelFactory
import com.example.testtask.domain.model.ImageOut
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageListFragment : Fragment(), RecyclerViewItemClickListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: ImageListViewModel by viewModels { factory }
    private var _binding: FragmentListPhotoBinding? = null
    private val binding get() = _binding!!
    private var imageId: Int = 0
    lateinit var imageListAdapter: ImageListAdapter

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

        addImage()
        setupRecyclerView()
        observeImage()
    }

    private fun showDeleteConfirmationDialog(imageOut: ImageOut) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Подтверждение удаления")
            .setMessage("Вы уверены, что хотите удалить изображение?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteImage(imageOut.id, imageOut)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun setupRecyclerView() {
        val imageListAdapter = ImageListAdapter(this)
        binding.photoRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = imageListAdapter
        }

    }

    private fun addImage() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.imageSharedFlow.collect { imageOut ->
                viewModel.addPhoto(imageOut)
            }
        }
    }

    private fun observeImage() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.imageFlow.collect {
                imageListAdapter.submitData(it)
            }
        }
    }


    override fun onItemLongClick(item: ImageOut): Boolean {
        showDeleteConfirmationDialog(item)
        return true
    }

    override fun onItemClick(item: ImageOut) {
        val action = ImageListFragmentDirections.actionNavPhotosToCommentsFragment(imageId)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}