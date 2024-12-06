package com.example.decentralizedfreelancemarketplace.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Sign in user with email and password
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<String> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success("Sign-in successful")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Invalid credentials"))
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("User already exists"))
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("User does not exist"))
        } catch (exception: Exception) {
            Result.failure(exception) // Catch other general exceptions
        }
    }


    // Create user with email and password
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<String> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success("Sign-up successful")
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("User already exists"))
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("Weak password"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Invalid credentials"))
        } catch (exception: Exception) {
            Result.failure(exception) // Catch other general exceptions
        }
    }



    // Fetch user role from Firestore
    suspend fun fetchUserRoleFromFirestore(userId: String): Result<String> {
        return try {
            val userRef = db.collection("users").document(userId)
            val document = userRef.get().await()
            val role = document.getString("role") ?: ""
            Result.success(role)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    // Get current authenticated user
    fun getAuthUser() = auth.currentUser

    // Fetch the role of the current authenticated user
    suspend fun getRole(): Result<String> {
        val user = auth.currentUser
        return if (user != null) {
            fetchUserRoleFromFirestore(user.uid) // Use the existing function to fetch role
        } else {
            Result.failure(Exception("User not authenticated"))
        }
    }

    private val firestore = FirebaseFirestore.getInstance()

    // Function to save user role to Firestore
    suspend fun saveUserRoleToFirestore(userId: String, role: String): Result<Unit> {
        return try {
            val userRoleMap = hashMapOf("role" to role)
            firestore.collection("users")
                .document(userId)
                .set(userRoleMap, SetOptions.merge())
                .await() // Wait for Firestore operation to complete
            Result.success(Unit) // Return success
        } catch (e: Exception) {
            Result.failure(e) // Return failure if an error occurs
        }
    }


}
