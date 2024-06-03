package com.example.mygreenactivities.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mygreenactivities.R
import com.example.mygreenactivities.databinding.FragmentSignInBinding
import com.example.mygreenactivities.view.MainActivity
import com.example.mygreenactivities.viewmodel.AuthViewModel

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.bSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Fields empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signIn(email, password).observe(viewLifecycleOwner) {
                    if (it.isSuccess) {
                        Toast.makeText(context, "Signed In", Toast.LENGTH_SHORT).show()
                        checkAuthState()
                    } else if (it.isFailure) {
                        Toast.makeText(context, "Incorrect Email or Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_container, SignUpFragment()).addToBackStack(null)
                .commit()
        }

        checkAuthState()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkAuthState() {
        if (viewModel.currentUser() != null) {
            val i = Intent(context, MainActivity::class.java)
            startActivity(i)
        }
    }
}