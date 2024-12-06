package com.example.decentralizedfreelancemarketplace.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.decentralizedfreelancemarketplace.ui.navigation.Screen
import com.example.decentralizedfreelancemarketplace.ui.screens.common.ProfileHeader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileBuyerScreen(navController: NavController, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    val context = LocalContext.current
    val user = auth.currentUser
    val userName = remember { mutableStateOf("") }
    val userRole = remember { mutableStateOf("") }

    // Fetch user data from Firestore
    LaunchedEffect(user) {
        user?.let {
            val firestore = FirebaseFirestore.getInstance()
            val userId = it.uid

            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        userName.value = document.getString("name") ?: "No Name"
                        userRole.value = document.getString("role") ?: "No Role"
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, "Error fetching user data: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    if (user == null) {
        navController.navigate(Screen.Login.route) {
            popUpTo(0) { inclusive = true }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ProfileHeader(userName.value, userRole.value)

            Spacer(modifier = Modifier.height(16.dp))

            // Buyer-specific content
            PurchaseHistory()

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(Screen.Home.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Home")
            }
        }
    }
}

@Composable
fun PurchaseHistory() {
    Column {
        Text(text = "Purchase History:")
        Spacer(modifier = Modifier.height(8.dp))
        // Dummy items for now
        Text(text = "1. Service A - Completed")
        Text(text = "2. Service B - Pending")
    }
}
