package com.example.androidmaster.parser


import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TlvReaderTest {

    private val sample = "00020101021129380009khqr@aclb0111855124883580206ACLEDA391300042CCY010145204599953031165802KH5918THEACHUMNITH CHOUN6010PHNOM PENH62130209070629984630453F7"

    @Test
    fun merchantNameIsRead() {
        assertEquals("THEACHUMNITH CHOUN", readTlv(sample)["59"])
    }

    @Test
    fun merchantCityIsRead() {
        assertEquals("PHNOM PENH", readTlv(sample)["60"])
    }

    @Test
    fun currencyIsRead() {
        assertEquals("116", readTlv(sample)["53"])
    }

    @Test
    fun amountIsAbsentForStaticQr() {
        assertNull(readTlv(sample)["54"])
    }
}