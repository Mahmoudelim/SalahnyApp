package com.khedma.salahny.prsentation.Login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.khedma.salahny.prsentation.SignUp.Navbar
import com.khedma.salahny.ui.theme.blue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ClientLoginScreen(navController: NavController) {
    val vm=loginViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val emailState= remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val imeAction = remember { mutableStateOf(ImeAction.Done) }
Column(modifier = Modifier.fillMaxSize()) {
    Navbar()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, start = 16.dp, end = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "")

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            leadingIcon = {

            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction.value
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                vm.login(email = emailState.value, passwordState.value) { success, message ->
                    if (success) {
                        // Login successful, navigate to the next screen or perform necessary actions
                        Log.i("message",message.toString())
                    } else {
                        // Login failed, show an error message
                        Log.i("message",message.toString())
                    }
                }
            }
                 ,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = blue, // Set background color
                contentColor = Color.White // Set text color (optional)
            )
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


}

@Preview(showBackground = true)
@Composable
fun ClientLoginScreenPreview() {


}
