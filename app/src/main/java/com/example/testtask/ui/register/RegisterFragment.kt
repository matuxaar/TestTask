package com.example.testtask.ui.register

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testtask.DaggerApp
import com.example.testtask.databinding.FragmentRegisterBinding
import com.example.testtask.di.viewmodel.ViewModelFactory
import com.example.testtask.ui.enter.EnterFragmentDirections
import javax.inject.Inject

class RegisterFragment: Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: RegisterViewModel by viewModels { factory }
    private var _binding: FragmentRegisterBinding? = null
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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hasTextIn()
        setupButton()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.liveData.observe(viewLifecycleOwner) { signUpUser ->
            if (signUpUser.login.isNotEmpty() && signUpUser.token.isNotEmpty() && signUpUser.userId.toString().isNotEmpty()) {
                navigateToPhotoListFragment()
                Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Sign up failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToPhotoListFragment() {
        val action = EnterFragmentDirections.actionEnterFragmentToNavPhotos()
        findNavController().navigate(action)
    }

    private fun isLoginRight(): Boolean {
        if (!binding.editTextLogin.text.matches(REGEX_LOGIN.toRegex())) {
            binding.editTextLogin.error = "ERROR"
            return false
        }
        return true
    }

    private fun isPasswordRight(): Boolean {
        if(binding.editTextPassword.text.length in 7..501) {
            return true
        } else {
            binding.editTextPassword.error = "ERROR"
            return false
        }
    }

    private fun hasTextIn() {
        checkEditText(binding.editTextLogin)
        checkEditText(binding.editTextPassword)
        checkEditText(binding.editTextPasswordConfirm)
    }

    private fun checkEditText(editText: EditText) {
        editText.addTextChangedListener(textWatcher)
    }

    private fun setupButton() {
        with(binding) {
            signUpButton.setOnClickListener {
                if (isLoginRight() && isPasswordRight()) {
                    viewModel.signUp(editTextLogin.text.toString(), editTextPassword.text.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()

        private const val REGEX_LOGIN = "^[a-z0-9_\\-\\.]+@+([a-z0-9_\\-\\.]+\\.)+[a-z]{2,4}\$"
    }
}