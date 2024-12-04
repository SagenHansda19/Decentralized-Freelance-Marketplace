package com.example.decentralizedfreelancemarketplace.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.decentralizedfreelancemarketplace.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    // State to prevent multiple navigations
    var isNavigating by remember { mutableStateOf(false) }
    val user = auth.currentUser

    if (user == null && !isNavigating) {
        // Redirect to login screen if no user is logged in
        isNavigating = true
        navController.navigate(Screen.Login.route) {
            popUpTo(0) { inclusive = true } // Clear the entire navigation stack
            launchSingleTop = true
        }
    } else if (user != null) {
        // Show the logged-in user's profile
        val email = user.email ?: "Unknown"
        val name = user.displayName ?: "Guest"

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "User Profile", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Name: $name")
            Text(text = "Email: $email")

            Spacer(modifier = Modifier.height(20.dp))

            // Button to navigate back to the Home screen
            Button(
                onClick = { navController.navigate(Screen.Home.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Home")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Button to log out
            Button(
                onClick = {
                    auth.signOut()
                    isNavigating = true
                    // Clear the back stack and navigate to the Login screen
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true } // Clear the entire stack
                        launchSingleTop = true // Avoid multiple Login screens in the stack
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Log Out")
            }
        }
    }
}
