package com.khedma.salahny.WorkerRequests

import androidx.compose.runtime.Composable
import com.khedma.salahny.data.Request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RequestScreen(viewModel: requestViewModel, workerPhone: String) {
    // Call to fetch the requests
    LaunchedEffect(workerPhone) {
        viewModel.fetchRequests(workerPhone)
    }

    val requests by viewModel.requests.observeAsState(emptyList())

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

        if (requests.isEmpty()) {
            Text(text = "No requests available")
        } else {
            LazyColumn {
                items(requests) { request ->
                    RequestItem(request)
                }
            }
        }
    }
}

@Composable
fun RequestItem(request: Request) {
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

        }
    }
}