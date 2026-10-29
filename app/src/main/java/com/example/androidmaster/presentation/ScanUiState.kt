package com.example.androidmaster.presentation

import com.example.androidmaster.model.KhqrInfo

sealed interface ScanUiState {
    data object Idle: ScanUiState
    data class Success(val info: KhqrInfo): ScanUiState
    data class Error(val message: String): ScanUiState

}