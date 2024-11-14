package com.khedma.salahny.di

import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import com.khedma.salahny.Repoistory.SalahlyRepo
import com.khedma.salahny.Utill.Constants.BASE_URL
import com.khedma.salahny.data.SalahlyApiService
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.text.Typography.dagger


@dagger.Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Singleton
    @dagger.Provides
    fun providePokemonRepository(
        api: SalahlyApiService
    ) = SalahlyRepo(api)
    @Singleton
    @dagger.Provides
    fun provideSalahelyApi() : SalahlyApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(SalahlyApiService ::class.java)
    }
}