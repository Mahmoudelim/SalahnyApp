package com.khedma.salahny.Repoistory

import android.util.Log
import com.khedma.salahny.data.SalahlyApiService
import com.khedma.salahny.data.Worker
import javax.inject.Inject
import dagger.hilt.android.scopes.ActivityScoped
import kotlin.text.Typography.dagger

@ActivityScoped

class SalahlyRepo @Inject constructor(
    private val apiService: SalahlyApiService
){

}