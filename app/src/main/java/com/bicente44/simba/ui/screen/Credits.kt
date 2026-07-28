package com.bicente44.simba.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bicente44.simba.ui.components.Overlay

@Composable
fun Credits(
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
) {
    Overlay(title = "Credits", onBack = onBack, onDismissAll = onDismissAll) {

    }
}