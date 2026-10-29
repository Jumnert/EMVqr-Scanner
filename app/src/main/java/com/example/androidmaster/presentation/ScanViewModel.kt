package com.example.androidmaster.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.androidmaster.parser.DecodeResult
import com.example.androidmaster.parser.decodeKhqr
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScanViewModel : ViewModel(){
    private val _state = MutableStateFlow<ScanUiState>(ScanUiState.Idle)
    val state: StateFlow<ScanUiState> = _state.asStateFlow()

    fun onScanned(raw: String){
        _state.value = when (val result = decodeKhqr(raw)){
            is DecodeResult.Failure -> ScanUiState.Error(result.reason)
            is DecodeResult.Success -> ScanUiState.Success(result.info)
        }
    }
    fun dismiss(){
        _state.value = ScanUiState.Idle
    }
}

