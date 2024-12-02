package com.example.decentralizedfreelancemarketplace.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.decentralizedfreelancemarketplace.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    val user = auth.currentUser

    if (user == null) {
        // Redirect to login screen if no user is logged in
        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Login.route) { inclusive = true }
            launchSingleTop = true
        }
    } else {
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

            Button(
                onClick = { navController.navigate(Screen.Home.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Home")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    auth.signOut()
                    // Clear the back stack and navigate to the login screen
                    navController.popBackStack(Screen.Login.route, inclusive = false)
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Log Out")
            }
        }
    }
}
