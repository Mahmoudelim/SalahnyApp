package com.khedma.salahny.prsentation.SignUp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.AlexanderiaNeghborhoods

import com.khedma.salahny.data.CairoNeghborhoods
import com.khedma.salahny.data.Client
import com.khedma.salahny.data.ElsharqiaNeighborhoods
import com.khedma.salahny.data.GetLocation
import com.khedma.salahny.data.Location
import com.khedma.salahny.data.QalyubiaNeighborhoods
import com.khedma.salahny.data.SalahlyApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientSignUpViewModel : ViewModel() {
    private var apiService:SalahlyApiService
    private val _validationError = MutableLiveData<String?>()
    val validationError: LiveData<String?> get() = _validationError
    private val getLocation = GetLocation()

    val context: Context = SalahlyApplication.getApplicationContext()




    init {
        val retrofit :Retrofit=Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://irregular-worker-connections-default-rtdb.firebaseio.com/")
            .build()
            apiService=retrofit.create(SalahlyApiService::class.java)
    }
    fun getUserLocation(context: Context, callback: (Location?) -> Unit) {
        var Clocation = Location(0.0, 0.0)
        getLocation.getCurrentLocation(
            context,
            onSuccess = { location ->
                Clocation = location
                Log.i("memeLocation", Clocation.toString())
                callback(Clocation)
            }
        ) { errorMessage ->
            // Handle location retrieval failure
            Log.e("LocationError", errorMessage)
            callback(null)
        }
    }

    fun registerUser(

        name: String,
        phone:String,
        email: String,
        password: String,
        state: String,
        neghiborhood:String,


    ) {
        if (isNameValid(name).equals(false) || isEmailValid(email).equals(false) || isPasswordValid(
                password
            ).equals(false)
        ) {
            _validationError.value = "Invalid input. Please check your data."
            return
        }


        getUserLocation(context) { location ->
            location?.let {

                val client = Client(name, phone, email, password, state, it, neghiborhood)
                // Continue with your registration logic


                // Assuming your registerClient method in SalahlyApiService takes a Map parameter
                val call: Call<Void> = apiService.registerClient(client)


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
            }
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                  Log.i("ClientSignUpViewModel","sucses")
                } else {
                    // Registration with Firebase Authentication failed
                    Log.e(
                        "ClientSignUpViewModel",
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

        return Neghiborhood
    }
    fun ConfirmPassword(password: String,confirmPassword:String):Boolean{
        val validConfirm=password.equals(confirmPassword)
      return validConfirm

    }
}