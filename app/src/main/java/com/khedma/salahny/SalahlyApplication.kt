package com.khedma.salahny

import android.app.Application
import android.content.Context
import com.khedma.salahny.data.SharedPreferencesManager

class SalahlyApplication:Application(){
    init {
        application = this

    }

    companion object {
        private lateinit var application: SalahlyApplication
        fun getApplicationContext():Context= application.applicationContext
    }
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this) // Initialize with Application context
    }

}