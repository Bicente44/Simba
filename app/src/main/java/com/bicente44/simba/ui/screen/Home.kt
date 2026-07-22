package com.bicente44.simba.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bicente44.simba.viewmodel.SimbaViewModel

@Composable
fun Home(viewModel: SimbaViewModel, onSettingsClicked: () -> Unit) {
    Text("Home")
}