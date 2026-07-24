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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bicente44.simba.model.ActivityState
import com.bicente44.simba.model.Mood
import com.bicente44.simba.model.calculateAge
import com.bicente44.simba.model.calculateMood
import com.bicente44.simba.viewmodel.SimbaViewModel
import com.bicente44.simba.R
import com.bicente44.simba.model.canPerformAnyAction
import com.bicente44.simba.ui.components.StatButton
import kotlinx.coroutines.delay

@Composable
fun Home(
    viewModel: SimbaViewModel,
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    var now by remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
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
        // Simba
        Image(
            painter = simbaPainterFor(state.activityState, mood),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )

        // Action buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(12.dp),
            ) {
                StatButton(
                    icon = painterResource(R.drawable.icon_cat_food),
                    statValue = state.hunger,
                    color = Color(0xFFfa8072),
                    onClick = { viewModel.onFeedClicked(15, 15) },
                    enabled = state.feedCooldown.canUse(now) && canPerformAnyAction(state, now)
                )
                StatButton(
                    icon = painterResource(R.drawable.icon_play),
                    statValue = state.happiness,
                    color = Color(0xFFfed88f),
                    onClick = { viewModel.onPlayClicked(15) },
                    enabled = state.playCooldown.canUse(now) && canPerformAnyAction(state, now)
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                StatButton(
                    icon = painterResource(R.drawable.icon_sleep),
                    statValue = state.energy,
                    color = Color(0xFFc4a9f9),
                    onClick = { viewModel.onSleepClicked(15) },
                    enabled = state.sleepCooldown.canUse(now) && canPerformAnyAction(state, now)
                )
                Spacer(modifier = Modifier.width(16.dp))
                StatButton(
                    icon = painterResource(R.drawable.icon_groom),
                    statValue = state.cleanliness,
                    color = Color(0xFFd7f6f9),
                    onClick = { viewModel.onCleanClicked(5) },
                    enabled = canPerformAnyAction(state, now)
                )
            }
        }

        // Layer 4: settings entry point
        // TODO: make bigger
        IconButton(
            onClick = onSettingsClicked,
            modifier = Modifier.align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.icon_settings),
                contentDescription = "Settings"
            )
        }
    }
}

/**
 * Helper function to call to paint the specific Simba.
 * TODO: Replace simba_silly stubs with proper images
 */
@Composable
fun simbaPainterFor(activity: ActivityState, mood: Mood): Painter {
    return when (activity) {
        ActivityState.IDLE -> when (mood) {
            Mood.HAPPY -> painterResource(R.drawable.simba_silly)
            Mood.SAD -> painterResource(R.drawable.simba_silly)
            Mood.TIRED -> painterResource(R.drawable.simba_silly)
            Mood.SICK -> painterResource(R.drawable.simba_silly)
            Mood.ANGRY -> painterResource(R.drawable.simba_silly)
            Mood.NEUTRAL -> painterResource(R.drawable.simba_silly)
        }
        ActivityState.EATING -> painterResource(R.drawable.simba_silly)
        ActivityState.PLAYING -> painterResource(R.drawable.simba_silly)
        ActivityState.SLEEPING -> painterResource(R.drawable.simba_silly)
        ActivityState.GROOMING -> painterResource(R.drawable.simba_silly)
    }
}