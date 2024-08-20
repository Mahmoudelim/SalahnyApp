package com.khedma.salahny.prsentation.Categories

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.lang.reflect.Modifier
@Composable
fun CarpenterDetailsScreen(workerViewModel :WorkerViewModel){
    val carpenter = workerViewModel.selectedWorker.observeAsState().value
     Log.i("selectWork",carpenter.toString())
    carpenter?.let {
        Column(modifier = androidx.compose.ui.Modifier.padding(16.dp)) {
            Text(text = "Name: ${it.name}", style = MaterialTheme.typography.h2)

            Text(text = "Phone: ${it.phone}", style = MaterialTheme.typography.body2)
            // Display other carpenter details
            Button(onClick = { /* Handle Request */ }) {
                Text(text = "Request Service")
            }
        }
    } ?: run {
        Text(text = "No worker selected", style = MaterialTheme.typography.body2)
        LaunchedEffect(Unit) {
            delay(10000)
            Log.i("after 1000",carpenter.toString())
        }
    }

}