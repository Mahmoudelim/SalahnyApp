package com.khedma.salahny

import android.app.Application
import android.content.Context

class SalahlyApplication:Application(){
    init {
        application = this
    }

    companion object {
        private lateinit var application: SalahlyApplication
        fun getApplicationContext():Context= application.applicationContext
    }

}