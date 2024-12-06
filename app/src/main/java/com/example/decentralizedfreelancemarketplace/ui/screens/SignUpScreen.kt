@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.decentralizedfreelancemarketplace.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.decentralizedfreelancemarketplace.ui.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.decentralizedfreelancemarketplace.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignUpScreen(navController: NavController, auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") } // State for the name
    val selectedRole = remember { mutableStateOf("buyer") } // "buyer" as default
    var passwordVisible by remember { mutableStateOf(false) } // State to toggle password visibility

    // Get the UserViewModel instance
    val userViewModel: UserViewModel = viewModel()

    // Firebase Firestore instance
    val firestore = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign Up", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        // Name field
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Email field
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Password field with show/hide functionality
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Role selection using RadioButtons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            RadioButton(
                selected = selectedRole.value == "buyer",
                onClick = { selectedRole.value = "buyer" }
            )
            Text(text = "Buyer", modifier = Modifier.padding(start = 4.dp))

            Spacer(modifier = Modifier.width(20.dp))

            RadioButton(
                selected = selectedRole.value == "freelancer",
                onClick = { selectedRole.value = "freelancer" }
            )
            Text(text = "Freelancer", modifier = Modifier.padding(start = 4.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Sign-up button
        Button(onClick = {
            if (email.value.isNotEmpty() && password.value.isNotEmpty() && name.value.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser // Ensure this is inside the completion listener
                            user?.let {
                                val userId = it.uid

                                val userData = hashMapOf(
                                    "name" to name.value,
                                    "role" to selectedRole.value
                                )
                                firestore.collection("users").document(userId)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        userViewModel.saveUserRoleToFirestore(userId, selectedRole.value)
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("FirestoreError", "Error saving data: ${e.message}")
                                        Toast.makeText(context, "Error saving data: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }

                            Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.Login.route)
                        } else {
                            Toast.makeText(context, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }

            } else {
                Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Sign Up")
        }
    }
}
