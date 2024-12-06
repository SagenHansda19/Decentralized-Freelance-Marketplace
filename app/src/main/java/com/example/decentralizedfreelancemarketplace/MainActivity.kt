import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.decentralizedfreelancemarketplace.data.FirebaseRepository
import com.example.decentralizedfreelancemarketplace.ui.navigation.AppNavHost
import com.example.decentralizedfreelancemarketplace.ui.theme.DecentralizedFreelanceMarketplaceTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DecentralizedFreelanceMarketplaceTheme {
                val navController = rememberNavController()
                val firebaseRepository = FirebaseRepository()

                // Use LaunchedEffect to launch coroutine for getRole
                LaunchedEffect(Unit) {
                    val userRole = getUserRole(firebaseRepository)
                    // You can update a state or do something with the role
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Pass the suspend function to AppNavHost
                    AppNavHost(
                        navController = navController,
                        getRole = { getUserRole(firebaseRepository) }
                    )
                }
            }
        }
    }

    // Use suspend function to get the user role
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
}
