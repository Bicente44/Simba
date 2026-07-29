package com.bicente44.simba.ui.screen

import androidx.compose.runtime.Composable
import com.bicente44.simba.ui.components.Overlay

@Composable
fun Gallery(
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
) {
    Overlay(title = "Gallery", onBack = onBack, onDismissAll = onDismissAll) {

    }
}