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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khedma.salahny.R
import com.khedma.salahny.data.SharedPreferencesManager
import com.khedma.salahny.data.Worker
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PainterListScreen(viewModel: painterCatViewModel = viewModel()) {
    val context = LocalContext.current
    var painters by remember { mutableStateOf<List<Worker>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }




    LaunchedEffect(Unit) {
        // Simulate loading delay
        delay(2000)

        // Fetch data
        painters = viewModel.getSortedWorker(context)

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
        Column(modifier = Modifier.padding(15.dp)) {
            Text(
                text = "Available Painter",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Gray

            )
            Spacer(modifier = Modifier.height(8.dp))

            // If not loading, display the list of plumbers
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            val filteredPainters = painters.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }
            // If not loading, display the list of plumbers
            LazyColumn {
                items(filteredPainters) { plumber ->
                    PainterItem(plumber)
                }
            }
        }
    }
}

@Composable
fun PainterItem(painter: Worker) {
    var isFavorite by remember { mutableStateOf(SharedPreferencesManager.getFavorites().any { it.name == painter.name }) }

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
                contentDescription = painter.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Column for plumber details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = painter.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = painter.phone,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${painter.neghborhood}, ${painter.state}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                // You can add the rating here
                // For example:
                // RatingBar(rating = plumber.rating)
            }
            IconButton(
                onClick = {
                    if (isFavorite) {
                        SharedPreferencesManager.removeFavorite(painter)
                    } else {
                        SharedPreferencesManager.addFavorite(painter)
                    }
                    isFavorite = !isFavorite
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) colorResource(id = R.color.Blue) else Color.Gray
                )
            }

        }
    }
}
