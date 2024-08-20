package com.khedma.salahny.prsentation.Request

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.khedma.salahny.R
import com.khedma.salahny.data.Worker

@Composable
fun WorkerDetailsScreen(worker: Worker) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display worker image
        Image(
            painter = painterResource(id = R.drawable.plumber), // Replace with actual image resource
            contentDescription = worker.name,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.background)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display worker name
        Text(
            text = worker.name,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display worker details
        Text(
            text = "Phone: ${worker.phone}",
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "Location: ${worker.neghborhood}, ${worker.state}",
            style = MaterialTheme.typography.body2
        )

        Spacer(modifier = Modifier.height(16.dp))
         // Request Button
        val blueColor = colorResource(id = R.color.Blue)
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = blueColor // Set the text color of the button
            ),
        ) {
            Text(text = "Request Worker")
        }
    }
}
