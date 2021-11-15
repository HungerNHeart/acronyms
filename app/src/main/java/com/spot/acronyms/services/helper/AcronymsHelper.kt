package com.spot.acronyms.services.helper

import com.spot.acronyms.services.NactemServices
import com.spot.acronyms.services.model.AcronymsResponse
import javax.inject.Inject

class AcronymsHelper @Inject constructor(private val service: NactemServices) {

    suspend fun getFull(shortForm: String): List<AcronymsResponse> = service.getLongForm(shortForm)
}