package com.khedma.salahny.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.IOException

class notficationViewModel :ViewModel() {
    var state by mutableStateOf(notificationState())
         private set

    private val api:FcmApi=Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    fun onRemoteTokenChange(newToken:String)
    {
        state=state.copy(
            remoteToken = newToken
        )
    }

    fun onSubmitRemoteToken()
    {
        state=state.copy(
            isEntringToken = false
        )
     }
    fun onMessageChange(message:String)
    {
        state=state.copy(
            message=message
        )
    }
    fun sendMessage(isBroadcast :Boolean){
        viewModelScope.launch {
            val messageDto = SendMessageDTO(
                to = if (isBroadcast) null else state.remoteToken,
                notification = NotificationBody(
                    title = "New message",
                    body = state.message
                )
            )
            try {
                if (isBroadcast){

                    api.broadcast(messageDto)
                }
                else
                {
                    api.sendMessage(messageDto)
                }
                state = state.copy(
                    message = "" ,

                )
            }
            catch (e: IOException)
            {
                e.printStackTrace()
            }
            catch (e: HttpException)
            {
                e.printStackTrace()
            }

        }

    }

}