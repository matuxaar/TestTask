package com.example.testtask.ui.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testtask.databinding.FragmentLoginBinding
import com.example.testtask.di.viewmodel.ViewModelFactory
import com.example.testtask.DaggerApp
import com.example.testtask.domain.model.User
import com.example.testtask.ui.enter.EnterFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: LogInViewModel by viewModels { factory }
    private var _binding: FragmentLoginBinding? = null
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        hasTextIn()
        setupButton()
    }

    private fun observeLiveData() {

        viewModel.liveData.observe(viewLifecycleOwner) {state ->
            when(state) {
                is LogInState.InProgress -> {

                }
                is LogInState.Success -> {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    navigateToPhotoListFragment()
                }
                is LogInState.Error -> {
                    Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun navigateToPhotoListFragment() {
        val action = EnterFragmentDirections.actionEnterFragmentToNavPhotos()
        findNavController().navigate(action)
    }

    private fun hasTextIn() {
        checkEditText(binding.editTextLogin)
        checkEditText(binding.editTextPassword)
    }

    private fun setupButton() {
        with(binding) {
            signUpButton.setOnClickListener {
                if (isLoginRight() && isPasswordRight()) {
                    viewModel.logIn(editTextLogin.text.toString(), editTextPassword.text.toString())
                }
            }
        }
    }

    private fun isLoginRight(): Boolean {
        if (!binding.editTextLogin.text.matches(REGEX_LOGIN.toRegex())) {
            binding.editTextLogin.error = "Email pattern is: test_test@test.test"
            return false
        }
        return true
    }

    private fun isPasswordRight(): Boolean {
        return if (binding.editTextPassword.text.length in 8..500) {
            true
        } else {
            binding.editTextPassword.error = "ERROR"
            false
        }
    }

    private fun checkEditText(editText: EditText) {
        editText.addTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = LogInFragment()

        private const val REGEX_LOGIN = "^[a-z0-9_\\-\\.]+@+([a-z0-9_\\-\\.]+\\.)+[a-z]{2,4}\$"
    }
}