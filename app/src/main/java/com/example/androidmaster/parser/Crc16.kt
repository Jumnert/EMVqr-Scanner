package com.example.androidmaster.parser



fun crc16(input: String): Int {
    var crc = 0xFFFF

    for (ch in input) {
        crc = crc xor (ch.code shl 8)

        repeat(8) {
            crc = if ((crc and 0x8000) != 0) {
                (crc shl 1) xor 0x1021
            } else {
                crc shl 1
            }
            crc = crc and 0xFFFF
        }
    }

    return crc
}

