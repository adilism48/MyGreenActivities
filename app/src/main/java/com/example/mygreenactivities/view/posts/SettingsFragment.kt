package com.example.mygreenactivities.view.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mygreenactivities.databinding.FragmentSettingsBinding
import com.example.mygreenactivities.view.auth.AuthActivity
import com.example.mygreenactivities.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        auth = Firebase.auth

        binding.bChangeEmail.setOnClickListener {
            val currentUserEmail = auth.currentUser?.email

            if (currentUserEmail != null) {
                auth.sendPasswordResetEmail(currentUserEmail).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "To change pass follow to your email", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Err", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            logout()
        }

        binding.bLogout.setOnClickListener {
            logout()
        }

        val myRef = Firebase.database.getReference("users")
        myRef.child(auth.currentUser?.uid ?: "Without UID").get().addOnSuccessListener {
            binding.sEmailVisibility.isChecked = it.child("showEmail").value as Boolean
            binding.tvInterest.text = it.child("interests").value.toString()
        }

        binding.sEmailVisibility.setOnCheckedChangeListener { _, isChecked ->
            myRef.child(auth.currentUser?.uid ?: "Without UID")
                .updateChildren(hashMapOf<String, Any?>("showEmail" to isChecked))
        }

        binding.tvEmail.text = auth.currentUser?.email ?: "Not auth"

        binding.bSaveInterests.setOnClickListener {
            val tags = binding.groupTag.checkedRadioButtonId

            if (tags == -1) {
                Toast.makeText(context, "Choose interests", Toast.LENGTH_SHORT).show()
            } else {
                val tag = (when (tags) {
                    binding.rbActivities.id -> "activities"
                    binding.rbThreads.id -> "threads"
                    else -> "other"
                })
                myRef.child(auth.currentUser?.uid ?: "Without UID")
                    .updateChildren(hashMapOf<String, Any?>("interests" to tag))
                myRef.child(auth.currentUser?.uid ?: "Without UID")
                    .get().addOnSuccessListener {
                        binding.tvInterest.text = it.child("interests").value.toString()
                        Toast.makeText(context, "Interests changed", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        return binding.root
    }

    private fun logout() {
        auth.signOut()
        val i = Intent(context, AuthActivity::class.java)
        startActivity(i)
    }
}