package com.khedma.salahny.prsentation.profile
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.khedma.salahny.R
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.SharedPreferencesManager
import com.khedma.salahny.prsentation.SignUp.SignUpForClient
import com.khedma.salahny.ui.theme.blue

@Composable
fun ProfileScreen() {
    val context=SalahlyApplication.getApplicationContext()
    val name= SharedPreferencesManager.name
    val email= SharedPreferencesManager.email
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Profile picture
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = colorResource(id = R.color.BLUE)

        )

        Spacer(modifier = Modifier.height(16.dp))

        // User name
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        Text(
            text = "john.doe@example.com",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Edit Profile Button

    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    ProfileScreen()
}

