package com.example.androidmaster.parser

import com.example.androidmaster.model.KhqrInfo

sealed interface DecodeResult {
    data class Success(val info: KhqrInfo) : DecodeResult
    data class Failure(val reason: String) : DecodeResult
}