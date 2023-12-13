package com.example.testtask.ui.enter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testtask.ui.login.LogInFragment
import com.example.testtask.ui.register.RegisterFragment
import java.lang.IllegalStateException

class EnterAppAdapter(
    fragment: EnterFragment
): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = PAGE_COUNT

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LogInFragment.newInstance()
            1 -> RegisterFragment.newInstance()
            else -> throw IllegalStateException()
        }
    }

    companion object {
        private const val PAGE_COUNT = 2
    }

}