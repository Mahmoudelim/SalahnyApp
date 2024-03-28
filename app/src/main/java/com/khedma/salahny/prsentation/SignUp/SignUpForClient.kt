package com.khedma.salahny.prsentation.SignUp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.khedma.salahny.ui.theme.blue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignUpForClient(navController: NavController){
Column {
    SignUpHeader()
    FormForClient(navController)
}


}
@Composable
fun SignUpHeader() {
    Text(
        text = "Sign Up",
        style = MaterialTheme.typography.headlineMedium.copy(
            color = blue, // You can adjust the color as needed
            fontSize = 28.sp, // Adjust the font size as needed
            textAlign = TextAlign.Center
        ),
        modifier = Modifier.padding(16.dp) // Adjust padding as needed
    )
}
@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun FormForClient(navController: NavController) {
    val vm = ClientSignUpViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val state = remember { mutableStateOf("") }
    val states = listOf("Cairo","Giza" ,"Alexandria", "Qalyubia", "Elsharqia" /* ... other states */)
    val neghborhood = remember { mutableStateOf("") }
    var isPasswordValid=true
    var isEmailValid=true
    var isPhoneValid=true
    var isNameValid=true
    var isPasswordConfirmed=true

    neighborhoods= listOf()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        item {
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it
                              isNameValid=vm.isNameValid(it)},
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (!isNameValid) {
                Text("you must Enter your Name", color = Color.Red,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it
                    isEmailValid=vm.isEmailValid(it)
                                },
                label = { Text("email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (!isEmailValid) {
                Text("Email must be like mahmoud@gmail.com", color = Color.Red,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))
            }

            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = phone.value,
                onValueChange = { phone.value = it
                                  isPhoneValid=vm.isPhoneValid(it)
                                },
                label = { Text("Phone") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (!isPhoneValid) {
                Text("phone must be have 11 numbers", color = Color.Red,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))
            }

            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it
                 isPasswordValid=vm.isPasswordValid(it)
                                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (!isPasswordValid) {
                Text("Password must contain @, numbers, and characters", color = Color.Red,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp))
            }

            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it
                    isPasswordConfirmed=vm.ConfirmPassword(password.value,it)
                                },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (!isPasswordConfirmed)
            Text("Confirmed email does not match", color = Color.Red,  modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp))

            Spacer(modifier = Modifier.height(15.dp))
            StateDropdown(
                label = "State :",
                items = states,
                selectedItem = state.value,
                onItemSelected = {
                    state.value = it
                },

            )
            Spacer(modifier = Modifier.height(15.dp))
            Dropdown(
                label = "Neighborhood",
                items = neighborhoods,
                selectedItem = neghborhood.value
            ) { neghborhood.value = it }
            Spacer(modifier = Modifier.height(15.dp))
        }





        item {
            Button(
                onClick = {
                    vm.registerUser(
                        name.value,
                        phone.value,
                        email.value,
                        password.value,
                        state.value,
                        neghborhood.value
                    )
                    if (vm.validationError.value==null) {
                        navController.navigate("ClientHome")
                    }
                    else
                    {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(vm.validationError.value.toString(),null,false,SnackbarDuration.Short)
                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue, // Set background color
                    contentColor = Color.White // Set text color (optional)
                )
            ) {
                Text("Register")
            }
        }
    }
}

@Composable
fun StateDropdown(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier

) {
    var expanded by remember { mutableStateOf(false) }
    val vm: ClientSignUpViewModel = viewModel()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Gray.copy(alpha = 0.1F))
                .clickable { expanded = true }
        ) {
            Text(
                text = selectedItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 12.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(0.dp, 150.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier.height(40.dp),
                    onClick = {
                        neighborhoods= vm.appendNeghiborhood(item)
                        expanded = false
                        onItemSelected(item)

                    }
                ) {
                    Text(item, modifier = Modifier.padding(start = 16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    SignUpForClient(navController = rememberNavController())
}