package dev.erick.pc02nasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.erick.pc02nasa.ui.ApodScreen
import dev.erick.pc02nasa.ui.theme.PC02NASATheme
import dev.erick.pc02nasa.viewmodel.ApodViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC02NASATheme {
                val apodViewModel: ApodViewModel = viewModel()
                ApodScreen(
                    viewModel = apodViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
