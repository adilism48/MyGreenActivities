package com.example.mygreenactivities.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygreenactivities.data.AuthRepositoryImpl
import com.example.mygreenactivities.model.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl

    fun signIn(email: String, password: String): LiveData<Result<AuthResult>> {
        val resultLiveData = MutableLiveData<Result<AuthResult>>()
        repository.signIn(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                resultLiveData.postValue(Result.success(it.result))
            } else {
                resultLiveData.postValue(Result.failure(it.exception!!))
            }
        }
        return resultLiveData
    }

    fun signUp(email: String, password: String): LiveData<Result<AuthResult>> {
        val resultLiveData = MutableLiveData<Result<AuthResult>>()
        repository.signUp(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                resultLiveData.postValue(Result.success(it.result))
            } else {
                resultLiveData.postValue(Result.failure(it.exception!!))
            }
        }
        return resultLiveData
    }

    fun currentUser(): FirebaseUser? {
        return repository.currentUser()
    }

    fun currentUserEmail():String? {
        return repository.currentUser()?.email
    }

    fun addUser(user: User) {
        repository.addUser(user)
    }
}