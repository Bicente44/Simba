package com.bicente44.simba.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun Home(
    viewModel: SimbaViewModel,
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val now = System.currentTimeMillis()

    val mood = remember(state) { calculateMood(state) }
    val age = remember(state) { calculateAge(state, now) }

    Box(modifier = modifier.fillMaxSize()) {
        // Layer 1: backdrop
        Image(
            painter = painterResource(R.drawable.bg_home),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Layer 2: Simba (activity photo if not idle, else mood photo)
        Image(
            painter = simbaPainterFor(state.activityState, mood),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )

        // Layer 3: action buttons (placeholder plain buttons for now)
        // TODO: Buttons are too low, home button is in the way, how to hide home and back buttons unless swipe up
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.onFeedClicked(15, 15) }) {
                Text("Feed")
            }
            Button(onClick = { viewModel.onPlayClicked(15) }) {
                Text("Play")
            }
            Button(onClick = { viewModel.onSleepClicked(15) }) {
                Text("Sleep")
            }
            Button(onClick = { viewModel.onCleanClicked(5) }) {
                Text("Clean")
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