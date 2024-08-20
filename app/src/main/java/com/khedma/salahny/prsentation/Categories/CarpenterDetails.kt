package com.khedma.salahny.prsentation.Categories

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khedma.salahny.data.Worker
import kotlinx.coroutines.delay

@Composable
fun CarpenterDetailsScreen(workerViewModel: WorkerViewModel) {
    val carpenter = workerViewModel.selectedWorker.observeAsState().value
    Log.i("selectWork", carpenter.toString())

    carpenter?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display the carpenter's image
            Image(
                painter = painterResource(id = it.imageRes), // Assuming imageResId is in Worker object
                contentDescription = it.name,
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
            )

            Text(text = "Name: ${it.name}", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Phone: ${it.phone}", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(16.dp))

            // Button with blue color
            Button(
                onClick = { /* Handle Request */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = com.khedma.salahny.R.color.Blue))
            ) {
                Text(text = "Request Service", color = Color.White)
            }
        }
    } ?: run {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No worker selected", style = MaterialTheme.typography.body2)
        }

        LaunchedEffect(Unit) {
            delay(10000)
            Log.i("after 1000", carpenter.toString())
        }
    }
}
