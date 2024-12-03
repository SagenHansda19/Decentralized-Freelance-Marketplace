package com.example.decentralizedfreelancemarketplace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.decentralizedfreelancemarketplace.ui.screens.HomeScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.ProfileScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.LoginScreen
import com.example.decentralizedfreelancemarketplace.ui.screens.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(navController: NavHostController) {
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

    // Set the start destination based on the user's authentication state
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route
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
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}

// Sealed class to represent screens
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object SignUp : Screen("signup")
}
