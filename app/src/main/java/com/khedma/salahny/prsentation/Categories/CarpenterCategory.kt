package com.khedma.salahny.prsentation.Categories

import CarpenterViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.khedma.salahny.R
import com.khedma.salahny.data.SharedPreferencesManager
import com.khedma.salahny.data.Worker
import com.khedma.salahny.prsentation.Login.workerViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarpenterListScreen(viewModel: CarpenterViewModel = viewModel(), navController: NavController,
                        workrViewModel: WorkerViewModel ) {
    val context = LocalContext.current
    var carpenters by remember { mutableStateOf<List<Worker>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        // Simulate loading delay
        delay(2000)

        // Fetch data
        carpenters = viewModel.getWorkerByProfession()

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
                Text(text = "Available Carpenter"
                ,
                style = MaterialTheme.typography.headlineLarge ,
                color = Color.Gray

            )
            Spacer(modifier = Modifier.height(8.dp))
            // Search View
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp ))

            // Filtered list based on search query
            val filteredCarpenters = carpenters.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            // Display the list of carpenters
            LazyColumn {
                items(filteredCarpenters) { carpenter ->
                    CarpenterItem(carpenter) {
                            selectedWorker ->
                        // Set the selected worker in the ViewModel
                        workrViewModel.selectWorker(selectedWorker)
                        Log.i("ssssworker",selectedWorker.toString())
                        // Navigate to the details screen
                        navController.navigate("carpenterDetails")
                    }
                    }
                }
            }
        }
    }


@Composable
fun CarpenterItem(carpenter: Worker,onItemClick:  (Worker) -> Unit) {
    var isFavorite by remember { mutableStateOf(SharedPreferencesManager.getFavorites().any { it.name == carpenter.name }) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(carpenter) }   ,
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
            // Display carpenter image
            Image(
                painter = painterResource(id = R.drawable.carpenter),
                contentDescription = carpenter.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Column for carpenter details
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
            }

            IconButton(
                onClick = {
                    if (isFavorite) {
                        SharedPreferencesManager.removeFavorite(carpenter)
                    } else {
                        SharedPreferencesManager.addFavorite(carpenter)
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
