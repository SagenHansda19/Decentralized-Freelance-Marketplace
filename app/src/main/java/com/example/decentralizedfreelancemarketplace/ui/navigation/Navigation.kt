package com.example.decentralizedfreelancemarketplace.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.decentralizedfreelancemarketplace.data.FirebaseRepository
import com.example.decentralizedfreelancemarketplace.ui.screens.HomeScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.ProfileScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.LoginScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.ProfileBuyerScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.ProfileFreelancerScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(navController: NavHostController) {
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null
    val firebaseRepository = FirebaseRepository()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, firebaseRepository = firebaseRepository)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController, getRole = { getUserRole(firebaseRepository) })
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}

private suspend fun getUserRole(firebaseRepository: FirebaseRepository): String {
    val user = FirebaseAuth.getInstance().currentUser
    return if (user != null) {
        // Fetch role using firebaseRepository
        val result = firebaseRepository.getRole()
        if (result.isSuccess) result.getOrNull() ?: "unknown" else "unknown"
    } else {
        "buyer" // Default role if user is not logged in
    }
}

@Composable
fun ProfileScreen(navController: NavHostController, firebaseRepository: FirebaseRepository) {
    var role by remember { mutableStateOf("") }

    // Using LaunchedEffect to call the suspend function getRole
    LaunchedEffect(Unit) {
        val result = firebaseRepository.getRole()
        role = if (result.isSuccess) result.getOrNull() ?: "unknown" else "unknown"
    }

    // Display the role or a loading state
    if (role.isEmpty()) {
        // Display loading or a placeholder
    } else {
        // Display the user's role
        Text(text = "User Role: $role")
    }
}

