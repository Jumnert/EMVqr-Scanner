package com.example.androidmaster.parser


import com.example.androidmaster.model.KhqrInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class KhqrDecoderTest {

    private val sample = "00020101021129380009khqr@aclb0111855124883580206ACLEDA391300042CCY010145204599953031165802KH5918THEACHUMNITH CHOUN6010PHNOM PENH62130209070629984630453F7"

    @Test
    fun validSampleDecodes() {
        val result = decodeKhqr(sample)
        assertEquals(
            DecodeResult.Success(
                KhqrInfo(
                    merchantName = "THEACHUMNITH CHOUN",
                    amount = null,
                    currency = "KHR",
                    merchantCity = "PHNOM PENH"
                )
            ),
            result
        )
    }

    @Test
    fun changedCharacterFailsCrc() {
        val tampered = sample.replaceFirst("THEACHUMNITH", "THEACHUMNITX")
        val result = decodeKhqr(tampered)
        assertTrue(result is DecodeResult.Failure)
        assertEquals("CRC mismatch", (result as DecodeResult.Failure).reason)
    }

    @Test
    fun tooShortFails() {
        assertTrue(decodeKhqr("123") is DecodeResult.Failure)
    }
}