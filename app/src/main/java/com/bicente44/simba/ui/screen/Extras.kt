package com.bicente44.simba.ui.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.bicente44.simba.model.internationalization.ExtraStringKey
import com.bicente44.simba.model.internationalization.ExtraStrings
import com.bicente44.simba.ui.components.Overlay
import com.bicente44.simba.viewmodel.SimbaViewModel

@Composable
fun Extra(
    viewModel: SimbaViewModel,
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
    onCreditsClicked: () -> Unit,
    onRewatchIntroClicked: () -> Unit,
    onGalleryClicked: () -> Unit,
    onDebugClicked: () -> Unit,
    ) {

    val settingsState by viewModel.settingsState.collectAsState()

    Overlay(title = ExtraStrings.get(ExtraStringKey.TITLE, settingsState.language), onBack = onBack, onDismissAll = onDismissAll) {
        Button(onClick = onCreditsClicked) { Text(text = ExtraStrings.get(ExtraStringKey.CREDITS_BUTTON, settingsState.language)) }
        Button(onClick = onRewatchIntroClicked) { Text(text = ExtraStrings.get(ExtraStringKey.REWATCH_INTRO_BUTTON, settingsState.language)) }
        Button(onClick = onGalleryClicked) { Text(text = ExtraStrings.get(ExtraStringKey.GALLERY_BUTTON, settingsState.language)) }
        if (viewModel.isDebug) {
            Button(
                onClick = onDebugClicked,
            ) { Text(text = "Debug",) }
        }
    }
}