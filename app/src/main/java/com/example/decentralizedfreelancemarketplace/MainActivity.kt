package com.example.decentralizedfreelancemarketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.decentralizedfreelancemarketplace.ui.navigation.AppNavHost
import com.example.decentralizedfreelancemarketplace.ui.theme.DecentralizedFreelanceMarketplaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DecentralizedFreelanceMarketplaceTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Using the modular AppNavHost function for navigation
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}
