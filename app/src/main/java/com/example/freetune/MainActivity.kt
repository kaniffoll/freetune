package com.example.freetune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.freetune.ui.BaseAppViewModel
import com.example.freetune.ui.theme.FreetuneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreetuneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BaseApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BaseApp(modifier: Modifier = Modifier) {
    val viewModel: BaseAppViewModel = hiltViewModel()

    var currentText by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(currentText)
        Button(onClick = {
            currentText = viewModel.getCurrentPitch()
        }) {
            Text("CLICK")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    BaseApp()
}
