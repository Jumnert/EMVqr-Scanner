package com.example.androidmaster.presentation

import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidmaster.model.KhqrInfo
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

@Composable
fun ScanScreens(viewModel: ScanViewModel = viewModel()) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val scanner = remember {
        GmsBarcodeScanning.getClient(
            context,
            GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    viewModel.onScanned(barcode.rawValue ?: "")
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Scan failed or cancelled", Toast.LENGTH_SHORT).show()
                }
        }) {
            Text("Scan QR")
        }
    }

    when (val s = state) {
        is ScanUiState.Success -> DetailsDialog(s.info, onDismiss = viewModel::dismiss)
        is ScanUiState.Error -> ErrorDialog(s.message, onDismiss = viewModel::dismiss)
        ScanUiState.Idle -> Unit
    }
}

@Composable
private fun DetailsDialog(info: KhqrInfo, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Payment details") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Merchant: ${info.merchantName}")
                Text("City: ${info.merchantCity ?: "-"}")
                Text("Currency: ${info.currency}")
                Text("Amount: ${info.amount ?: "Not set"}")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("OK") }
        }
    )
}

@Composable
private fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Invalid QR") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("OK") }
        }
    )
}