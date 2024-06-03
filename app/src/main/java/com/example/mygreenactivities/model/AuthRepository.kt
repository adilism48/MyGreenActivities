package com.example.mygreenactivities.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun signIn(email: String, password: String): Task<AuthResult>
    fun signUp(email: String, password: String): Task<AuthResult>
    fun currentUser(): FirebaseUser?
    fun addUser(user: User)
}