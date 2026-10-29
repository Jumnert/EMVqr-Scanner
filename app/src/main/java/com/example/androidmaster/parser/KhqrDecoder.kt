package com.example.androidmaster.parser

import com.example.androidmaster.model.KhqrInfo

fun decodeKhqr(raw: String): DecodeResult {
    if (raw.length < 8) {
        return DecodeResult.Failure("Code is too short")
    }

    // The CRC covers everything up to and including "6304"
    val body = raw.dropLast(4)
    if (!body.endsWith("6304")) {
        return DecodeResult.Failure("Missing CRC tag")
    }

    val expected = "%04X".format(crc16(body))
    val actual = raw.takeLast(4).uppercase()
    if (expected != actual) {
        return DecodeResult.Failure("CRC mismatch")
    }

    val tags = readTlv(raw)
    val name = tags["59"]
        ?: return DecodeResult.Failure("Missing merchant name")

    return DecodeResult.Success(
        KhqrInfo(
            merchantName = name,
            amount = tags["54"],
            currency = currencyName(tags["53"]),
            merchantCity = tags["60"]
        )
    )
}

private fun currencyName(code: String?): String = when (code) {
    "840" -> "USD"
    "116" -> "KHR"
    else -> code ?: "UNKNOWN"
}