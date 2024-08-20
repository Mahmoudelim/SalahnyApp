package com.khedma.salahny

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
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khedma.salahny.prsentation.Categories.CarpenterDetailsScreen
import com.khedma.salahny.prsentation.Categories.CarpenterListScreen
import com.khedma.salahny.prsentation.Categories.CarpenterViewModel
import com.khedma.salahny.prsentation.Categories.ElectricianListScreen
import com.khedma.salahny.prsentation.Categories.PainterListScreen
import com.khedma.salahny.prsentation.Categories.PlumberListScreen
import com.khedma.salahny.prsentation.Categories.WorkerViewModel
import com.khedma.salahny.prsentation.ClientHome.AppBar
import com.khedma.salahny.prsentation.ClientHome.BottomNavigationBar
import com.khedma.salahny.prsentation.ClientHome.ClientHomeScreen
import com.khedma.salahny.prsentation.Login.workerViewModel
import com.khedma.salahny.prsentation.profile.FavoritesScreen
import com.khedma.salahny.prsentation.profile.ProfileScreen

class MainActivity : ComponentActivity() {
    private val workerViewModel: WorkerViewModel by viewModels()
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
                        salahlyAroundApp(workerViewModel)

                    }
                }
            }
        }
    }


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun salahlyAroundApp(workerViewModel: WorkerViewModel) {
  val navController= rememberNavController()

    LaunchedEffect(Unit) {
        // Simulate navigation after splash screen delay (replace with your logic)
        kotlinx.coroutines.delay(2000) // Adjust delay as needed
        navController.navigate("ClientHome")
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
            composable(route = "favourite") {
                FavoritesScreen()
            }
            composable(route = "ClientHome") {
                ClientHomeScreen(navController = navController)
            }
            composable(route = "favourites") {
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
            composable("signUpWorker") {
                SignUpScreenForWorker(navController)
            }
            composable("Plumber") {
                PlumberListScreen()
            }
            composable("Carpenter") {

               CarpenterListScreen(viewModel = CarpenterViewModel(), navController = navController, workrViewModel = workerViewModel)
            }
            composable("carpenterDetails") {

                CarpenterDetailsScreen(workerViewModel = workerViewModel)
            }
            composable("Painter") {
                PainterListScreen()
            }
            composable("Electrician") {
                ElectricianListScreen()
            }
            composable("signUpClient") {
                SignUpForClient(navController = navController)
            }
            composable("ClientLogin") {
                ClientLoginScreen(navController)
            }
    }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SalahnyTheme {

    }
}