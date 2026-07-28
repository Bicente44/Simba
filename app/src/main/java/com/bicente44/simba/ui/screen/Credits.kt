package com.bicente44.simba.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bicente44.simba.ui.components.Overlay

@Composable
fun Credits(
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
) {
    Overlay(title = "Credits", onBack = onBack, onDismissAll = onDismissAll) {
        val uriHandler = LocalUriHandler.current
        Text(
            "Developer",
            fontWeight = FontWeight.Bold
        )
        Text("Vincent Welbourne")

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            "Art Direction",
            fontWeight = FontWeight.Bold
        )
        Text("Vincent Welbourne")

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            "Visit my other work",
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {
            uriHandler.openUri("https://github.com/Bicente44")
        }) {
            Text("My GitHub")
        }
        Button(onClick = {
            uriHandler.openUri("https://bicente44.github.io/bicente_website")
        }) {
            Text("My Portfolio")
        }
    }
}