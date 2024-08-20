package com.khedma.salahny.data

import android.icu.text.CaseMap.Title
import com.google.firebase.messaging.RemoteMessage.Notification

data class SendMessageDTO(
    val to:String? ,
    val notification:NotificationBody
)

data class NotificationBody (
    val title: String ,
    val body: String
)


