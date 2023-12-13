package com.example.testtask.ui.enter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testtask.DaggerApp
import com.example.testtask.databinding.FragmentEnterBinding
import com.google.android.material.tabs.TabLayoutMediator

class EnterFragment: Fragment() {

    private var _binding: FragmentEnterBinding? = null
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
    ): View {
        _binding = FragmentEnterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
    }

    private fun setupPager() {
        val pager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = EnterAppAdapter(this)
        pager.adapter = adapter

        TabLayoutMediator(tabLayout, pager) {tab, pos ->
            tab.text = when(pos) {
                0 -> "LogIn"
                1 -> "Register"
                else -> "LogIn"
            }
        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}