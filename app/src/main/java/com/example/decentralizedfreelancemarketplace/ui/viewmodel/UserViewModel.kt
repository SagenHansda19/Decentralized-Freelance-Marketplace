package com.example.decentralizedfreelancemarketplace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.decentralizedfreelancemarketplace.data.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val firebaseRepository: FirebaseRepository = FirebaseRepository()) : ViewModel() {

    // Handle Sign In
    fun signInWithEmailAndPassword(email: String, password: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val result = firebaseRepository.signInWithEmailAndPassword(email, password)
            onResult(result.getOrElse { "Login Failed: ${it.message}" })
        }
    }

    // Handle Sign Up
    fun createUserWithEmailAndPassword(email: String, password: String, role: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val result = firebaseRepository.createUserWithEmailAndPassword(email, password)
            if (result.isSuccess) {
                // Save role to Firestore
                val userId = firebaseRepository.getAuthUser()?.uid ?: ""
                firebaseRepository.saveUserRoleToFirestore(userId, role)
                onResult("Sign-up Successful")
            } else {
                onResult("Sign-up Failed: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    // Fetch User Role
    fun fetchUserRole(userId: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val result = firebaseRepository.fetchUserRoleFromFirestore(userId)
            onResult(result.getOrElse { "Failed to fetch role" })
        }
    }

    private val _userRole = MutableStateFlow<String>("")
    val userRole: StateFlow<String> = _userRole

    // Function to fetch user role
    fun fetchUserRole(userId: String) {
        viewModelScope.launch {
            // Fetch the role using the repository
            val result = firebaseRepository.fetchUserRoleFromFirestore(userId)
            if (result.isSuccess) {
                _userRole.value = result.getOrNull() ?: "" // Set the fetched role
            }
        }
    }

    fun saveUserRoleToFirestore(userId: String, role: String) {
        viewModelScope.launch {
            val result = firebaseRepository.saveUserRoleToFirestore(userId, role)
            if (result.isSuccess) {
                // Handle success (role saved)
            } else {
                // Handle error
            }
        }
    }
}
