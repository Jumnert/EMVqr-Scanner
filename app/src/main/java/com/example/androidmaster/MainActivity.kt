package com.example.androidmaster

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidmaster.presentation.ScanScreens
import com.example.androidmaster.ui.theme.AndroidMasterTheme
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScanScreens()
        }
    }
}

@Composable
fun ScanScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var scannedText by remember { mutableStateOf<String?>(null) }

    val scanner = remember {
        GmsBarcodeScanning.getClient(
            context,
            GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build()

        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = {
                scanner.startScan()
                    .addOnSuccessListener {
                        barcode -> scannedText = barcode.rawValue ?: "(no text found)"
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Scan Failed", Toast.LENGTH_SHORT).show()
                    }

            }
        ) {
            Text("Scan QR")
        }
    }
    scannedText?.let {
        text ->
        AlertDialog(
            onDismissRequest = { scannedText = null},
            title = { Text("Scan Result")},
            text = { Text(text)},
            confirmButton = {
                TextButton(onClick = {scannedText = null}) {
                    Text("ok")
                }
            }

        )
    }

}