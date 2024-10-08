package com.khedma.salahny.prsentation.WelcomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.khedma.salahny.R
import com.khedma.salahny.ui.theme.blue

@SuppressLint("SuspiciousIndentation")
@Composable
fun WelcomeScreen(navController: NavController) {


    var selectedRole = rememberSaveable { mutableStateOf<UserRole?>(null) }
    LazyColumn(modifier = Modifier.fillMaxSize()){
        item {
            WelcomeContent()
            Spacer(modifier = Modifier.height(5.dp))
            RoleSelection(selectedRole = selectedRole, onRoleSelected = {},navController)
        }




    }
}
@Composable

fun WelcomeContent() {
    // Use Box to allow flexible positioning
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Welcome to Salahny!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Need help? Find reliable workers nearby for any task.\n" +
                            "Looking for work? Discover flexible jobs that fit your skills and schedule.\n" +
                            "Join us today!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Image(
                painter = painterResource(id = R.drawable.myicon),
                contentDescription = "App icon",
                modifier = Modifier
                    .size(120.dp) // Adjust size as needed
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun RoleSelection(
    selectedRole: MutableState<UserRole?>,
    onRoleSelected: (UserRole) -> Unit,
    navController: NavController
) {
    val vm = WelcomeScreenViewModel()

    Column(modifier = Modifier.padding(10.dp)) {
        Text("Choose if you are a Worker or Client")
        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            RadioButton(
                selected = selectedRole.value == UserRole.WORKER,
                onClick = {
                    selectedRole.value = UserRole.WORKER
                    onRoleSelected(UserRole.WORKER)
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = blue,
                    unselectedColor = Color.Gray
                )
            )
            Text(text = "Worker")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedRole.value == UserRole.CLIENT,
                onClick = {
                    selectedRole.value = UserRole.CLIENT
                    onRoleSelected(UserRole.CLIENT)
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Blue,
                    unselectedColor = Color.Gray
                )
            )
            Text("Client")
        }

        Spacer(modifier = Modifier.height(5.dp))

        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "New to Salahny? Create an account to unlock the possibilities!")
            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    selectedRole.value?.let {
                        vm.setUserRoleAndNavigate(it, navController)
                    }
                },
                modifier = Modifier.align(CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
// Empty function needed for preview
    WelcomeScreen(navController = rememberNavController())
}


