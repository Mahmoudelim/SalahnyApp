package com.khedma.salahny.prsentation.WelcomeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.khedma.salahny.data.SharedPreferencesManager

class WelcomeScreenViewModel(): ViewModel() {

        fun setUserRoleAndNavigate(role: UserRole, navController: NavController) {
            Log.i("role", role.name)
            SharedPreferencesManager.userRole = role.name.lowercase()

            when (role) {
                UserRole.WORKER -> navController.navigate("signUpWorker")
                UserRole.CLIENT -> navController.navigate("signUpClient")
            }
        }
    }

