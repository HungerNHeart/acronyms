package com.spot.acronyms.services

import com.spot.acronyms.services.model.AcronymsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NactemServices {

    @GET("dictionary.py")
    suspend fun getLongForm(@Query("sf") shortForm: String): List<AcronymsResponse>
}