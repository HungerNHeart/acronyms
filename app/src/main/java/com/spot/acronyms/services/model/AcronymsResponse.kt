package com.spot.acronyms.services.model

import com.google.gson.annotations.SerializedName

data class AcronymsResponse(
    @SerializedName("sf")
    var shortForm: String = "",
    @SerializedName("lfs")
    var longForm: List<LongFormData> = mutableListOf()
)

data class LongFormData(
    @SerializedName("lf")
    var longForm: String = "",
    @SerializedName("freq")
    var frequency: Int = 0,
    @SerializedName("since")
    var since: Int = 0,
)
