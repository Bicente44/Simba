package com.bicente44.simba.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bicente44.simba.model.internationalization.CreditsStringKey
import com.bicente44.simba.model.internationalization.CreditsStrings
import com.bicente44.simba.model.internationalization.SettingsStrings
import com.bicente44.simba.ui.components.Overlay
import com.bicente44.simba.viewmodel.SimbaViewModel

@Composable
fun Credits(
    viewModel: SimbaViewModel,
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
) {
    val settingsState by viewModel.settingsState.collectAsState()

    Overlay(title = CreditsStrings.get(CreditsStringKey.TITLE, settingsState.language), onBack = onBack, onDismissAll = onDismissAll) {
        val uriHandler = LocalUriHandler.current
        Text(
            text = CreditsStrings.get(CreditsStringKey.DEVELOPER_LABEL, settingsState.language),
            fontWeight = FontWeight.Bold
        )
        Text("Vincent Welbourne")

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = CreditsStrings.get(CreditsStringKey.ART_DIRECTION_LABEL, settingsState.language),
            fontWeight = FontWeight.Bold
        )
        Text("Vincent Welbourne")

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = CreditsStrings.get(CreditsStringKey.VISIT_WORK_LABEL, settingsState.language),
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {
            uriHandler.openUri("https://github.com/Bicente44")
        }) {
            Text(text = CreditsStrings.get(CreditsStringKey.GITHUB_BUTTON, settingsState.language))
        }
        Button(onClick = {
            uriHandler.openUri("https://bicente44.github.io/bicente_website")
        }) {
            Text(text = CreditsStrings.get(CreditsStringKey.PORTFOLIO_BUTTON, settingsState.language))
        }
    }
}