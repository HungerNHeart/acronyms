package com.spot.acronyms.repository

import com.spot.acronyms.services.helper.AcronymsHelper
import javax.inject.Inject

class AcronymsRepository @Inject constructor(private val helper: AcronymsHelper) {

    suspend fun getLongFrom(shortForm: String) = helper.getFull(shortForm)
}