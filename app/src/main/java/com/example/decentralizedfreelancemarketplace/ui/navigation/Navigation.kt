package com.example.decentralizedfreelancemarketplace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.decentralizedfreelancemarketplace.ui.screens.HomeScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.ProfileScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.LoginScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route // Use Screen enum for better maintainability
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
    }
}

// Sealed class to represent screens
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Login : Screen("login")
}
