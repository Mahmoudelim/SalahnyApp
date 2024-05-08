package com.khedma.salahny.prsentation.Login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.Client
import com.khedma.salahny.data.SalahlyApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class workerViewModel :ViewModel(){
    val context: Context = SalahlyApplication.getApplicationContext()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    private var apiService: SalahlyApiService
    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://irregular-worker-connections-default-rtdb.firebaseio.com/")
            .build()
        apiService=retrofit.create(SalahlyApiService::class.java)
    }
    fun login(email: String, password: String, scaffoldState: ScaffoldState, navController: NavController) {
        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchUserData(email)
                    navController.navigate("WorkerHome")

                } else {
                    viewModelScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Login failed. ${task.exception?.message}")
                        Log.i("err","Login failed. ${task.exception?.message}")
                    }
                }
            }
    }

    private fun fetchUserData(email: String) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("worker")

        databaseReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val userData = data.getValue(Client::class.java)
                        userData?.let { saveUserDataToSharedPreferences(it)
                            Log.i("dtalogin",userData.toString())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("fetchUserData", "Error: ${error.message}")
                }
            })
    }


    private fun saveUserDataToSharedPreferences(userData: Client) {
        with(sharedPreferences.edit()) {
            putString("workerName", userData.name)
            putString("WorkerEmail", userData.email)
            putString("workerPhone",userData.phone)
            apply()
        }

    }

    @Composable
    fun rememberScaffoldState(): ScaffoldState {
        val state = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        DisposableEffect(state) {
            onDispose {
                scope.launch {
                    state.snackbarHostState.currentSnackbarData?.dismiss()
                }
            }
        }

        return state
    }

}