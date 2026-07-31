package com.bicente44.simba.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bicente44.simba.model.calculateAge
import com.bicente44.simba.model.calculateMood
import com.bicente44.simba.viewmodel.SimbaViewModel
import com.bicente44.simba.R
import com.bicente44.simba.model.canPerformAnyAction
import com.bicente44.simba.model.internationalization.HomeStringKey
import com.bicente44.simba.model.internationalization.HomeStrings
import com.bicente44.simba.model.isDead
import com.bicente44.simba.ui.components.PettableSimba
import com.bicente44.simba.ui.components.StatButton
import kotlinx.coroutines.delay

@Composable
fun Home(
    viewModel: SimbaViewModel,
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val settingsState by viewModel.settingsState.collectAsState()
    var now by remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(150)
            now = System.currentTimeMillis()
        }
    }

    val mood = remember(state) { calculateMood(state) }
    val age = remember(state) { calculateAge(state, now) }

    Box(modifier = modifier.fillMaxSize()) {

        // Background
        Image(
            painter = painterResource(R.drawable.bg_home),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Age + Settings row
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = HomeStrings.ageLabel(age, settingsState.language))

            if (isDead(state)) {
                Button(
                    onClick = { viewModel.onRestartSimba() }
                ) {
                    Text(text = HomeStrings.get(HomeStringKey.RESTART_BUTTON, settingsState.language))
                }
            }

            IconButton(onClick = onSettingsClicked, modifier = Modifier.size(55.dp)) {
                Image(
                    painter = painterResource(R.drawable.icon_settings),
                    contentDescription = "Settings",
                )
            }
        }

        // Simba
        PettableSimba(
            activityState = state.activityState,
            mood = mood,
            isDead = isDead(state),
            onPetTick = viewModel::onPetTick,
            onPetEnded = viewModel::onPetEnded,
            modifier = Modifier.align(Alignment.Center)
        )

        // Health + Action buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Health bar
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator(
                    progress = { state.health / 100f },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(24.dp),
                    color = Color(0xFFFFA332)
                )
                Text(
                    text = "${state.health}%",
                    color = Color.Black
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(12.dp),
            ) {
                StatButton(
                    icon = painterResource(R.drawable.icon_cat_food),
                    statValue = state.hunger,
                    color = Color(0xFFfa8072),
                    onClick = { viewModel.onFeedClicked(15, 15, 7) },
                    enabled = state.feedCooldown.canUse(now) && canPerformAnyAction(state, now) && !isDead(state)
                )
                StatButton(
                    icon = painterResource(R.drawable.icon_play),
                    statValue = state.happiness,
                    color = Color(0xFFfed88f),
                    onClick = { viewModel.onPlayClicked(15, 7) },
                    enabled = state.playCooldown.canUse(now) && canPerformAnyAction(state, now) && !isDead(state)
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                StatButton(
                    icon = painterResource(R.drawable.icon_sleep),
                    statValue = state.energy,
                    color = Color(0xFFc4a9f9),
                    onClick = { viewModel.onSleepClicked(15, 5, 5) },
                    enabled = state.sleepCooldown.canUse(now) && canPerformAnyAction(state, now) && !isDead(state)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StatButton(
                    icon = painterResource(R.drawable.icon_groom),
                    statValue = state.cleanliness,
                    color = Color(0xFFd7f6f9),
                    onClick = { viewModel.onCleanClicked(3) },
                    enabled = canPerformAnyAction(state, now) && !isDead(state)
                )
            }
        }
    }
}

