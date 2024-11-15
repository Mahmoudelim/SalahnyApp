package com.khedma.salahny

import CarpenterViewModel
import ElectricianCatViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.khedma.salahny.prsentation.Login.ClientLoginScreen
import com.khedma.salahny.prsentation.SignUp.SignUpForClient
import com.khedma.salahny.prsentation.SignUp.SignUpScreenForWorker
import com.khedma.salahny.prsentation.WelcomeScreen.SplashScreen
import com.khedma.salahny.prsentation.WelcomeScreen.WelcomeScreen
import com.khedma.salahny.ui.theme.SalahnyTheme
import com.khedma.salahny.data.RequestPermission
import android.Manifest
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.play.integrity.internal.w
import com.khedma.salahny.Repoistory.SalahlyRepo
import com.khedma.salahny.WorkerRequests.RequestScreen
import com.khedma.salahny.WorkerRequests.requestViewModel
import com.khedma.salahny.data.SalahlyApiService
import com.khedma.salahny.data.SharedPreferencesManager
import com.khedma.salahny.prsentation.Categories.CarpenterDetailsScreen
import com.khedma.salahny.prsentation.Categories.CarpenterListScreen
import com.khedma.salahny.prsentation.Categories.ElectricianListScreen
import com.khedma.salahny.prsentation.Categories.PainterListScreen
import com.khedma.salahny.prsentation.Categories.PlumberCatViewModel
import com.khedma.salahny.prsentation.Categories.PlumberListScreen
import com.khedma.salahny.prsentation.Categories.WorkerViewModel
import com.khedma.salahny.prsentation.Categories.painterCatViewModel
import com.khedma.salahny.prsentation.ClientHome.AppBar
import com.khedma.salahny.prsentation.ClientHome.BottomNavigationBar
import com.khedma.salahny.prsentation.ClientHome.ClientHomeScreen
import com.khedma.salahny.prsentation.Login.WorkerLoginScreen
import com.khedma.salahny.prsentation.Login.workerViewModel
import com.khedma.salahny.prsentation.WelcomeScreen.UserRole
import com.khedma.salahny.prsentation.WorkerHome.workerHome
import com.khedma.salahny.prsentation.profile.FavoritesScreen
import com.khedma.salahny.prsentation.profile.ProfileScreen

class MainActivity : ComponentActivity() {
    private val workerViewModel: WorkerViewModel by viewModels()
    private val requestViewModel: requestViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalahnyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                        RequestPermission(permission =  Manifest.permission.ACCESS_FINE_LOCATION)
                        salahlyAroundApp(workerViewModel, requestViewModel =requestViewModel )

                    }
                }
            }
        }
    }


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun salahlyAroundApp(workerViewModel: WorkerViewModel,requestViewModel: requestViewModel) {
    val navController= rememberNavController()
    val isLoggedIn = SharedPreferencesManager.email?.isNotEmpty() == true
    val userRole = SharedPreferencesManager.userRole
    Log.i("usrole","$userRole")
    val startDestination = if (isLoggedIn) {
        Log.i("usrole","$userRole")
        when (userRole) {

            "worker" -> "WorkerHome"
            "client" -> "ClientHome"
            else -> "WorkerLogin"
        }
    } else {
        "welcome2"
    }

    LaunchedEffect(Unit) {
        // Simulate navigation after splash screen delay (replace with your logic)
        kotlinx.coroutines.delay(2000) // Adjust delay as needed
        navController.navigate(startDestination)
    }
    Scaffold(
        topBar = {
            AppBar()
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)


        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "splash", Modifier.padding(innerPadding)) {
            composable(route = "splash") {
                SplashScreen()
            }
            composable("welcome2") {
                WelcomeScreen(
                    onLoginClick = { navController.navigate("ClientLogin") },
                    onRegisterClick = { navController.navigate("signUpclient") }
                )
            }
            composable(route = "favourite") {
                FavoritesScreen()
            }
            composable(route = "ClientHome") {
                ClientHomeScreen(navController = navController)
            }

            composable(route = "profile") {
                ProfileScreen(navController = navController)
            }
            composable("welcome") {
                WelcomeScreen(navController = navController)
            }
            composable("location") {
                RequestPermission(permission =  Manifest.permission.ACCESS_FINE_LOCATION)
            }
            composable("signUpworker") {
                SignUpScreenForWorker(navController)
            }
            composable("Plumber") {
                PlumberListScreen(viewModel = PlumberCatViewModel(), navController = navController, workerViewModel = workerViewModel)
            }
            composable("Carpenter") {

               CarpenterListScreen(viewModel = CarpenterViewModel(), navController = navController, workrViewModel = workerViewModel)
            }
            composable("carpenterDetails") {

                CarpenterDetailsScreen(workerViewModel = workerViewModel)
            }
            composable("Painter") {
                PainterListScreen(viewModel = painterCatViewModel(), navController = navController,
                    workerViewModel)
            }
            composable("Electrician") {
                ElectricianListScreen(viewModel = ElectricianCatViewModel(), navController = navController, workrViewModel = workerViewModel)
            }
            composable("signUpclient") {
                SignUpForClient(navController = navController)
            }
            composable("ClientLogin") {
                ClientLoginScreen(navController)
            }
            composable("WorkerLogin") {
                WorkerLoginScreen(navController)
            }
            composable("WorkerHome") {
                workerHome(navController)
            }
            composable("requests") {
                val workerPhone=SharedPreferencesManager.phone
                RequestScreen(requestViewModel , workerPhone.toString())
            }
    }


    }



}

@Composable
fun WorkerBottomNavigationBar(navController: NavController) {
    BottomNavigation( backgroundColor = MaterialTheme.colorScheme.background) {
        // Define your worker-specific navigation items here
        var selectedItem by remember { mutableStateOf("home") }
        BottomNavigationItem(
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = null,   tint = if (selectedItem == "home") colorResource(id = R.color.Blue) else Color.Gray) },
            selected =selectedItem == "home",
            onClick = {
                selectedItem="home"
                navController.navigate("WorkerHome")
            }
        )
        BottomNavigationItem(
            label = { Text("Requests") },
            icon = { Icon(Icons.Default.List, contentDescription = null , tint = if (selectedItem == "Requests") colorResource(id = R.color.Blue) else Color.Gray) },
            selected = selectedItem == "Requests",
            onClick = {
                selectedItem ="Requests"
                navController.navigate("requests")
            }
        )
        BottomNavigationItem(
            label = { Text("Profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = null, tint = if (selectedItem == "profile") MaterialTheme.colorScheme.primary else Color.Gray) },
            selected = selectedItem == "profile",
            onClick = {
                selectedItem="profile"
                navController.navigate("profile")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SalahnyTheme {

    }
}