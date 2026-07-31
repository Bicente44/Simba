package com.bicente44.simba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bicente44.simba.model.ActivityState
import com.bicente44.simba.model.Language
import com.bicente44.simba.model.Mood
import com.bicente44.simba.model.internationalization.HomeStringKey
import com.bicente44.simba.model.internationalization.HomeStrings.get

@Composable
fun SpeechBubble(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = text, color = Color.Black)
    }
}

fun speechFor(activity: ActivityState, mood: Mood, language: Language): String = when (activity) {
    ActivityState.PETTING -> get(HomeStringKey.SPEECH_PETTING, language)
    ActivityState.EATING -> get(HomeStringKey.SPEECH_EATING, language)
    ActivityState.PLAYING -> get(HomeStringKey.SPEECH_PLAYING, language)
    ActivityState.SLEEPING -> get(HomeStringKey.SPEECH_SLEEPING, language)
    ActivityState.GROOMING -> get(HomeStringKey.SPEECH_GROOMING, language)
    ActivityState.IDLE -> when (mood) {
        Mood.HAPPY -> get(HomeStringKey.SPEECH_HAPPY, language)
        Mood.SAD -> get(HomeStringKey.SPEECH_SAD, language)
        Mood.TIRED -> get(HomeStringKey.SPEECH_TIRED, language)
        Mood.SICK -> get(HomeStringKey.SPEECH_SICK, language)
        Mood.ANGRY -> get(HomeStringKey.SPEECH_ANGRY, language)
        Mood.NEUTRAL -> get(HomeStringKey.SPEECH_NEUTRAL, language)
    }
}