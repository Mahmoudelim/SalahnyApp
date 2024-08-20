package com.khedma.salahny.prsentation.profile

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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.khedma.salahny.R
import com.khedma.salahny.data.SharedPreferencesManager
import com.khedma.salahny.data.Worker
import com.khedma.salahny.prsentation.Categories.PlumberItem

@Composable
fun FavoritesScreen() {
    // Retrieve the list of favorite workers from SharedPreferences
    val favorites = SharedPreferencesManager.getFavorites()

    // Check if the favorites list is empty
    if (favorites.isEmpty()) {
        EmptyFavoritesScreen()
    } else {
        LazyColumn (modifier = Modifier.padding(16.dp)) {
            items(favorites) { worker ->
                FavouriteItem(worker = worker)
            }
        }
    }
}

@Composable
fun EmptyFavoritesScreen() {
    // UI for empty favorites
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No Favorites Yet",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Optional: Add an icon or graphic here
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "No favorites",
                tint = Color.Gray,
                modifier = Modifier.size(80.dp)
            )
        }
    }
}
@Composable
fun FavouriteItem(worker: Worker) {


    Card(
        modifier = Modifier
            .padding(16.dp)
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
                painter = painterResource(id = R.drawable.worker),
                contentDescription = worker.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Column for plumber details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = worker.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = worker.phone,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${worker.neghborhood}, ${worker.state}",
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
