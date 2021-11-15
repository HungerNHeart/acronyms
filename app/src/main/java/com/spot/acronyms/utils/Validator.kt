package com.spot.acronyms.utils

import com.spot.acronyms.services.NetworkStatus

object Validator {

    //user can only entry string only with alphabets and numerical values.
    fun validateSearch(query: String) =
        query.trim().isNotBlank() && query.length>1 && query.matches("^[a-zA-Z0-9]*$".toRegex())

    fun needRefresh(oldValue: NetworkStatus?, newValue: NetworkStatus): Boolean
    = oldValue != newValue && newValue == NetworkStatus.CONNECTED

}