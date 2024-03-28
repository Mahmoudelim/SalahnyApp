package com.khedma.salahny.prsentation.Categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khedma.salahny.R
import com.khedma.salahny.data.Worker
import kotlinx.coroutines.delay

@Composable
fun CarpenterListScreen(viewModel: CarpenterViewModel = viewModel()) {
    val context = LocalContext.current
    var plumbers by remember { mutableStateOf<List<Worker>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }




    LaunchedEffect(Unit) {
        // Simulate loading delay
        delay(2000)

        // Fetch data
        plumbers = viewModel.getSortedWorker(context)

        // After fetching data, set loading to false
        isLoading = false
    }

    if (isLoading) {
        // If loading, display a loading indicator
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // If not loading, display the list of plumbers
        LazyColumn {
            items(plumbers) { plumber ->
                CarpenterItem(plumber)
            }
        }
    }
}

@Composable
fun CarpenterItem(carpenter: Worker) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display plumber image
            Image(
                painter = painterResource(id = R.drawable.carpenter),
                contentDescription = carpenter.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Column for plumber details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = carpenter.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = carpenter.phone,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${carpenter.neghborhood}, ${carpenter.state}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                // You can add the rating here
                // For example:
                // RatingBar(rating = plumber.rating)
            }
        }
    }
}
