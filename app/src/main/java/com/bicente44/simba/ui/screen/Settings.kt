package com.bicente44.simba.ui.screen

import com.bicente44.simba.model.Language
import com.bicente44.simba.ui.components.Overlay
import com.bicente44.simba.viewmodel.SimbaViewModel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bicente44.simba.model.displayName
import com.bicente44.simba.model.internationalization.SettingsStringKey
import com.bicente44.simba.model.internationalization.SettingsStrings

@Composable
fun Settings(
    viewModel: SimbaViewModel,
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
    onExtrasClicked: () -> Unit
) {
    val settingsState by viewModel.settingsState.collectAsState()
    var languageMenuExpanded by remember { mutableStateOf(false) }

    Overlay(title = SettingsStrings.get(SettingsStringKey.TITLE, settingsState.language), onBack = onBack, onDismissAll = onDismissAll) {
        Text(text = SettingsStrings.get(SettingsStringKey.AUDIO_SFX_HEADER, settingsState.language))

        Row {
            Text(text = SettingsStrings.get(SettingsStringKey.MUSIC_VOLUME_LABEL, settingsState.language))
            Spacer(Modifier.size(10.dp))
            Slider(
                value = settingsState.musicVolume,
                onValueChange = viewModel::onMusicVolumeChanged,
                valueRange = 0f..1f,
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            Text(text = SettingsStrings.get(SettingsStringKey.SFX_VOLUME_LABEL, settingsState.language))
            Spacer(Modifier.size(10.dp))
            Slider(
                value = settingsState.sfxVolume,
                onValueChange = viewModel::onSfxVolumeChanged,
                valueRange = 0f..1f,
                modifier = Modifier.weight(1f)
            )
        }

        Box {
            Button(onClick = { languageMenuExpanded = true }) {
                Text(settingsState.language.displayName)
            }
            DropdownMenu(
                expanded = languageMenuExpanded,
                onDismissRequest = { languageMenuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(Language.ENGLISH.displayName) },
                    onClick = {
                        viewModel.onLanguageChanged(Language.ENGLISH)
                        languageMenuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(Language.FRENCH.displayName) },
                    onClick = {
                        viewModel.onLanguageChanged(Language.FRENCH)
                        languageMenuExpanded = false
                    }
                )
            }
        }
        Button(onClick = onExtrasClicked) { Text(text = SettingsStrings.get(SettingsStringKey.EXTRAS_BUTTON, settingsState.language)) }
    }
}