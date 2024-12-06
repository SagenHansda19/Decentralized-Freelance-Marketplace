package com.example.decentralizedfreelancemarketplace.ui.screens

import android.widget.Toast
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun ProfileScreen(navController: NavController, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    val context = LocalContext.current
    val user = auth.currentUser
    val userName = remember { mutableStateOf("") }
    val userRole = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(true) }

    // Fetch user data from Firestore
    LaunchedEffect(user) {
        user?.let {
            try {
                Log.d("ProfileScreen", "Fetching user data for UID: ${it.uid}")
                val firestore = FirebaseFirestore.getInstance()
                val userId = it.uid

                // Fetch the user document from Firestore
                val document = firestore.collection("users").document(userId).get().await()

                // Log document data for debugging
                Log.d("ProfileScreen", "Document fetched: ${document.data}")

                if (document.exists()) {
                    // Retrieve name and role from Firestore document
                    userName.value = document.getString("name") ?: "No Name"
                    userRole.value = document.getString("role") ?: "No Role"

                    // Log the retrieved values for debugging
                    Log.d("ProfileScreen", "Name: ${userName.value}, Role: ${userRole.value}")
                } else {
                    Log.d("ProfileScreen", "No document found for user ID: $userId")
                    Toast.makeText(context, "No user data found", Toast.LENGTH_SHORT).show()
                }
            } catch (exception: Exception) {
                Log.e("ProfileScreen", "Error fetching user data: ${exception.message}")
                Toast.makeText(context, "Error fetching user data: ${exception.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading.value = false
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading.value) {
            // Show a loading indicator while data is being fetched
            CircularProgressIndicator()
        } else {
            // Display user name and role once data is loaded
            Text(text = "Name: ${userName.value}")
            Text(text = "Role: ${userRole.value}")
        }
    }
}
