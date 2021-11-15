package com.spot.acronyms

import com.google.common.truth.Truth.assertThat
import com.spot.acronyms.services.NetworkStatus
import com.spot.acronyms.utils.Validator
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest{

    @Test
    fun validInput(){
        assertThat(Validator.validateSearch("hmm")).isEqualTo(true)
        assertThat(Validator.validateSearch("html")).isEqualTo(true)
        assertThat(Validator.validateSearch("o2")).isEqualTo(true)
    }

    @Test
    fun inValidInput(){
        assertThat(Validator.validateSearch("")).isEqualTo(false)
        assertThat(Validator.validateSearch("h")).isEqualTo(false)
        assertThat(Validator.validateSearch("h,")).isEqualTo(false)
        assertThat(Validator.validateSearch("h,.m")).isEqualTo(false)
    }

    @Test
    fun needRefresh(){
        assertThat(Validator.needRefresh(NetworkStatus.DISCONNECTED, NetworkStatus.CONNECTED)).isEqualTo(true)
    }

    @Test
    fun noNeedRefresh(){
        assertThat(Validator.needRefresh(NetworkStatus.DISCONNECTED, NetworkStatus.DISCONNECTED)).isEqualTo(false)
        assertThat(Validator.needRefresh(NetworkStatus.CONNECTED, NetworkStatus.DISCONNECTED)).isEqualTo(false)
        assertThat(Validator.needRefresh(NetworkStatus.CONNECTED, NetworkStatus.CONNECTED)).isEqualTo(false)
    }

}