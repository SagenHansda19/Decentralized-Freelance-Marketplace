package com.example.decentralizedfreelancemarketplace.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.decentralizedfreelancemarketplace.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to the Decentralized Freelance Marketplace!")

        Spacer(modifier = Modifier.height(20.dp))

        if (user != null) {
            // If the user is logged in, show Profile and Logout options
            Button(onClick = { navController.navigate(Screen.Profile.route) }) {
                Text(text = "Go to Profile")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.popBackStack(Screen.Login.route, inclusive = false)
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
//                        launchSingleTop = true
                    }
                },
//                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Log Out")
            }
        } else {
            // If the user is not logged in, show Login option
            Button(onClick = { navController.navigate(Screen.Login.route) }) {
                Text(text = "Go to Login")
            }
        }
    }
}
