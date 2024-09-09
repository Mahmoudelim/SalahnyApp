package com.khedma.salahny.WorkerRequests

import android.util.Log
import android.util.Log.v
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import com.khedma.salahny.data.Request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

@Composable
fun RequestScreen(viewModel: requestViewModel, workerPhone: String) {
    // Call to fetch the requests
    val requests by viewModel.requests.observeAsState(emptyList())
    LaunchedEffect(workerPhone) {
        viewModel.fetchRequests(workerPhone)
        Log.i("RequestScreenp", "Fetching requests for worker: $workerPhone")
        delay(1000)
        Log.i("RequestScreen", "Fetching requests for worker: $requests")
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Requests",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )



            LazyColumn {
                items(requests) { request ->
                    RequestItem(
                        request = request,
                        onAccept = { acceptedRequest ->
                            // Handle accept action here (e.g., update the request in the database)
                        },
                        onIgnore = { ignoredRequest ->
                            // Handle ignore action here (e.g., update the request as ignored)
                        }
                    )

                }
            }
        }

    }


@Composable
fun RequestItem(request: Request,
                onAccept: (Request) -> Unit,
                onIgnore: (Request) -> Unit) {
    val timestamp: Long =System.currentTimeMillis()
    val vm =requestViewModel()
    var timeAgoText by remember { mutableStateOf(vm.timeAgo(timestamp)) }
    LaunchedEffect(Unit) {
        while (true) {
            timeAgoText = vm.timeAgo(timestamp)
            delay(60 * 1000L) // Update every 1 minute (60,000 milliseconds)
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "You have a Request Service From: ${request.name}", style = MaterialTheme.typography.body1)
            Text(text = "his Phone: ${request.phone}", style = MaterialTheme.typography.body2)
            Text(text =timeAgoText, style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly ,
                verticalAlignment = Alignment.CenterVertically
            ) {


                    // Accept button
                    IconButton(
                        onClick = { onAccept(request) },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                            
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Accept",
                            tint = Color.White
                        )
                    }
                Spacer(modifier = Modifier.width(12.dp))

                    // Ignore button
                    IconButton(
                        onClick = { onIgnore(request) },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Ignore",
                            tint = Color.White
                        )
                    }
                }

            }

        }

    }

