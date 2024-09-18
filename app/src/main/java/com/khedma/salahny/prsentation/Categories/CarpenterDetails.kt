package com.khedma.salahny.prsentation.Categories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.SharedPreferencesManager
import com.khedma.salahny.data.Worker
import kotlinx.coroutines.delay

@Composable
fun CarpenterDetailsScreen(workerViewModel: WorkerViewModel) {
    val carpenter = workerViewModel.selectedWorker.observeAsState().value
    val UserPhone= SharedPreferencesManager.phone
    val UserImg= SharedPreferencesManager.ImgRes
    val userName=SharedPreferencesManager.name
    val viewModel :CdetailsVM = viewModel()
    val context =SalahlyApplication.getApplicationContext()
    var showDialog by remember { mutableStateOf(false) }
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
                painter = painterResource(id =  com.khedma.salahny.R.drawable.plumber), // Assuming imageResId is in Worker object
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
                onClick = {
                    if (UserPhone != null) {
                        if (userName != null) {
                            if (UserImg != null) {
                                showDialog=true
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = com.khedma.salahny.R.color.Blue))
            ) {
                Text(text = "Request Service", color = Color.White)
            }
            if (showDialog==true){
                AlertDialog(onDismissRequest = { showDialog=false },
                    title = { Text(text ="Confirm Request")}
                    ,
                    text = {
                        Text(text = "Are you sure you want to request the service?")
                    }
                    , confirmButton = { TextButton(onClick = {
                         val timestamp=System.currentTimeMillis()
                        if (UserPhone != null) {
                            if (userName != null) {
                                if (UserImg != null) {
                                    viewModel.OnRequest(
                                        it.phone,
                                        userPhone = UserPhone,
                                        userName,
                                        imgRes = UserImg ,
                                        timestamp
                                    )
                                    showToast(context, "Request sent successfully!")
                                }
                            }


                        } // Perform request action
                        showDialog = false // Close the dialog
                    }) {
                        Text("Confirm")
                    } }
                ,
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false // Close dialog without action
                        }) {
                            Text("Cancel")
                        }

                    }

                )
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
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}