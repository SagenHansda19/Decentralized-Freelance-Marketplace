package com.example.decentralizedfreelancemarketplace.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.decentralizedfreelancemarketplace.data.FirebaseRepository
import com.example.decentralizedfreelancemarketplace.ui.screens.LoginScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.SignUpScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(navController: NavHostController, getRole: suspend () -> String) {
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
            // Pass getRole here
            LoginScreen(navController = navController, getRole = getRole)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}
