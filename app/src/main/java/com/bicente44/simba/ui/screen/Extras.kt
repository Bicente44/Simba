package com.bicente44.simba.ui.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bicente44.simba.ui.components.Overlay

@Composable
fun Extra(
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
    onCreditsClicked: () -> Unit,
    onRewatchIntroClicked: () -> Unit,
    ) {
    Overlay(title = "Extras", onBack = onBack, onDismissAll = onDismissAll) {

        Button(onClick = onCreditsClicked) { Text("Credits") }
        Button(onClick = onRewatchIntroClicked) { Text("Rewatch Intro") }
    }
}