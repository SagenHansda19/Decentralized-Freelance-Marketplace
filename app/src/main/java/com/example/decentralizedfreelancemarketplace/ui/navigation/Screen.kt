package com.example.decentralizedfreelancemarketplace.ui.navigation

// Sealed class to represent the different screens/routes in the app
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object SignUp : Screen("signup")
}
