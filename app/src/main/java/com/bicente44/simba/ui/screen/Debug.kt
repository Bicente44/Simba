package com.bicente44.simba.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bicente44.simba.model.DebugStat
import com.bicente44.simba.ui.components.Overlay
import com.bicente44.simba.viewmodel.SimbaViewModel

@Composable
fun Debug(
    viewModel: SimbaViewModel,
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
) {
    Overlay(title = "Debug", onBack = onBack, onDismissAll = onDismissAll) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DebugStatRow("Hunger", DebugStat.HUNGER, viewModel::onDebugAdjustStat)
            DebugStatRow("Energy", DebugStat.ENERGY, viewModel::onDebugAdjustStat)
            DebugStatRow("Cleanliness", DebugStat.CLEANLINESS, viewModel::onDebugAdjustStat)
            DebugStatRow("Happiness", DebugStat.HAPPINESS, viewModel::onDebugAdjustStat)
            DebugStatRow("Health", DebugStat.HEALTH, viewModel::onDebugAdjustStat)
        }
    }
}

@Composable
private fun DebugStatRow(label: String, stat: DebugStat, onAdjust: (DebugStat, Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = label, modifier = Modifier.width(90.dp))
        Column {
            Row {
                Button(onClick = { onAdjust(stat, -1) }) { Text("-1") }
                Button(onClick = { onAdjust(stat, -10) }) { Text("-10") }
            }
            Row {
                Button(onClick = { onAdjust(stat, 1) }) { Text("+1") }
                Button(onClick = { onAdjust(stat, 10) }) { Text("+10") }
            }
        }
    }
}