package com.example.decentralizedfreelancemarketplace.ui.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

@Composable
fun ProfileHeader(userName: String, userRole: String) {
    Column {
        Text(text = "Name: $userName", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Role: $userRole", style = MaterialTheme.typography.bodyMedium)
    }
}
