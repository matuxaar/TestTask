package com.example.testtask.ui.checkauth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testtask.DaggerApp
import com.example.testtask.databinding.FragmentCheckAuthBinding
import com.example.testtask.di.viewmodel.ViewModelFactory
import javax.inject.Inject

class CheckAuthFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: CheckAuthViewModel by viewModels { factory }
    private var _binding: FragmentCheckAuthBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isAuth()
        viewModel.checkAuth()
    }

    private fun isAuth() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.liveData.observe(viewLifecycleOwner) {
            val navController = findNavController()
            if (it) {
                val action = CheckAuthFragmentDirections.actionCheckAuthFragmentToNavPhotos()
                navController.navigate(action)
            } else {
                val action = CheckAuthFragmentDirections.actionCheckAuthFragmentToEnterFragment()
                navController.navigate(action)
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}