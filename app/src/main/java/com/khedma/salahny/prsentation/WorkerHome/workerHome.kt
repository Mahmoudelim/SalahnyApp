package com.khedma.salahny.prsentation.WorkerHome

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.livedata.observeAsState
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
import com.khedma.salahny.data.SharedPreferencesManager
import com.khedma.salahny.prsentation.Categories.WorkerViewModel
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
    val viewModel =WorkerHomeViewModel()
    val availability by viewModel.availability.observeAsState(false)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp) ,
        Arrangement.Center ,

    ) {
        Text(
            text = "Your Availability",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp, top = 12.dp)
        )

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Row ( modifier = Modifier.weight(1f)) {
                Text(
                    text = "Status :",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 10.dp)

                    )
                 Text(
                text = if (availability) "Available" else "Not Available",
                color = if (availability) blue else Color.Gray,
                style = MaterialTheme.typography.labelLarge
            )
            }

            Switch(
                checked = availability,
                onCheckedChange = { newStatus ->
                    viewModel.changeAvailability(SharedPreferencesManager.email.toString(), newStatus)
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    workerHome(navController = rememberNavController())
}

