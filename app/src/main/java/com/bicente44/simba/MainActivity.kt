package com.bicente44.simba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.bicente44.simba.ui.SimbaApp
import com.bicente44.simba.ui.theme.SimbaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimbaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SimbaApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}