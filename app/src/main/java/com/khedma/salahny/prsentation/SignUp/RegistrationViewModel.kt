package com.khedma.salahny.prsentation.SignUp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.khedma.salahny.R
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.AlexanderiaNeghborhoods

import com.khedma.salahny.data.CairoNeghborhoods
import com.khedma.salahny.data.Client

import com.khedma.salahny.data.ElsharqiaNeighborhoods
import com.khedma.salahny.data.GetLocation
import com.khedma.salahny.data.QalyubiaNeighborhoods
import com.khedma.salahny.data.SalahlyApiService
import com.khedma.salahny.data.Worker
import com.khedma.salahny.data.gizaNeighborhoods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrationViewModel :ViewModel(){
    private var apiService: SalahlyApiService
    private val _validationError = MutableLiveData<String?>()
    val validationError: LiveData<String?> get() = _validationError
    val vm=ClientSignUpViewModel()
    private val getLocation = GetLocation()
    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://irregular-worker-connections-default-rtdb.firebaseio.com/")
            .build()
        apiService=retrofit.create(SalahlyApiService::class.java)
    }

    val context: Context = SalahlyApplication.getApplicationContext()


    fun registerUser(

        name: String,
        email: String,
        phone: String,
        password: String,
        proffesion:String,
        state: String,
        neghiborhood:String,


    ) {
        if (isNameValid(name).equals(false) || isEmailValid(email).equals(false) || isPasswordValid(password).equals(false)) {
            _validationError.value = "Invalid input. Please check your data."
            return
        }


       vm.getUserLocation(context) { location ->
            location?.let {

                val Worker = Worker(name,email,phone,password,proffesion, state,neghiborhood ,it,false,0.0,
                    R.drawable.plumber)
                // Continue with your registration logic


                // Assuming your registerClient method in SalahlyApiService takes a Map parameter
                val call: Call<Void> = apiService.registerWorker(Worker)


                // Assuming your registerClient method in SalahlyApiService takes a Client parameter


                // Asynchronously execute the POST request
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        // Handle successful registration
                        if (response.isSuccessful) {

                            Log.i("respo", "ok")
                        } else {
                            Log.i("respo", "Error: ${response.code()}")
                            response.errorBody()
                            // You can extract error information from response.errorBody()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.i("respo", t.message.toString())


                    }

                })
            }}
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    Log.i("WorkerViewModel","sucses")
                } else {
                    // Registration with Firebase Authentication failed
                    Log.e(
                        "WorkerViewModel",
                        "Registration failed: ${authTask.exception?.message}"
                    )
                }

            }

    }

    fun isPhoneValid(phone: String): Boolean{
        val validPhone=phone.length==11
        return validPhone
    }
    fun isPasswordValid(password: String): Boolean {
        val containsSpecialChar = password.any { it.isLetterOrDigit().not() }
        val containsNumber = password.any { it.isDigit() }
        val containsChar = password.any { it.isLetter() }

        return containsSpecialChar && containsNumber && containsChar
    }

    fun isEmailValid(email: String): Boolean {
        val validEmail= android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return validEmail
    }

    fun isNameValid(name: String): Boolean {
        val validName= name.isNotBlank()
        return validName
    }

    @SuppressLint("SuspiciousIndentation")
    fun appendNeghiborhood(state: String) : List<String>{
        var Neghiborhood= listOf<String>()
        if(state=="Cairo")
        {
            Neghiborhood= CairoNeghborhoods
        }
        else if (state=="Alexandria"){Neghiborhood= AlexanderiaNeghborhoods}
        else if (state=="Qalyubia"){Neghiborhood= QalyubiaNeighborhoods}
        else if (state=="Elsharqia"){Neghiborhood= ElsharqiaNeighborhoods}
        else if (state=="Giza"){Neghiborhood= gizaNeighborhoods }
        return Neghiborhood
    }
    fun ConfirmPassword(password: String,confirmPassword:String):Boolean{
        val validConfirm=password.equals(confirmPassword)
        return validConfirm

    }



}
