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

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to the Decentralized Freelance Marketplace!")

        Spacer(modifier = Modifier.height(20.dp))

        // Button to go to Profile screen
        Button(onClick = { navController.navigate(Screen.Profile.route) }) {
            Text(text = "Go to Profile")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Button to go to Login screen
        Button(onClick = { navController.navigate(Screen.Login.route) }) {
            Text(text = "Go to Login")
        }
    }
}
