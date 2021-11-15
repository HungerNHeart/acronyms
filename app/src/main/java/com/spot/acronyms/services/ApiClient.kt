package com.spot.acronyms.services

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {
    private const val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"


    @Singleton
    @Provides
    fun provideRetroFit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun getNactemService(retroFit: Retrofit) = retroFit.create(NactemServices::class.java)
}