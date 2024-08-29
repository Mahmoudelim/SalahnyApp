package com.khedma.salahny.prsentation.WorkerHome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.khedma.salahny.prsentation.ClientHome.BottomNavigationBar
import com.khedma.salahny.prsentation.ClientHome.ClientHomeScreen
import com.khedma.salahny.ui.theme.blue


@Composable
fun workerHome(navController :NavController){
    Column {

        AvailabilitySection()


    }
}
@Composable
fun AvailabilitySection() {
    var isAvailable by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Availability",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Row ( modifier = Modifier.weight(1f)) {
                Text(
                    text = "Status:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 10.dp)

                    )
                 Text(
                text = if (isAvailable) "Available" else "Not Available",
                color = if (isAvailable) blue else Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
            }

            Switch(
                checked = isAvailable,
                onCheckedChange = { isAvailable = it }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    workerHome(navController = rememberNavController())
}

