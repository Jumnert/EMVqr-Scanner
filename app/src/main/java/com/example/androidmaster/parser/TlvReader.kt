package com.example.androidmaster.parser


fun readTlv(data: String): Map<String, String> {
    val result = mutableMapOf<String, String>()
    var index = 0

    while (index + 4 <= data.length) {
        val tag = data.substring(index, index + 2)
        val length = data.substring(index + 2, index + 4).toIntOrNull() ?: break

        val valueStart = index + 4
        val valueEnd = valueStart + length
        if (valueEnd > data.length) break

        result[tag] = data.substring(valueStart, valueEnd)
        index = valueEnd
    }

    return result
}