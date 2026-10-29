package com.example.androidmaster.parser

import org.junit.Assert.assertEquals
import org.junit.Test

class Crc16Test {

    @Test
    fun standardCheckValue() {
        assertEquals(0x29B1, crc16("123456789"))
    }

    @Test
    fun khqrSampleMatchesChecksum() {
        val sample = "00020101021129380009khqr@aclb0111855124883580206ACLEDA391300042CCY010145204599953031165802KH5918THEACHUMNITH CHOUN6010PHNOM PENH62130209070629984630453F7"
        val body = sample.dropLast(4)
        assertEquals("53F7", "%04X".format(crc16(body)))
    }
}