package com.bicente44.simba.ui.components

import androidx.compose.runtime.key
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.IntOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.bicente44.simba.R
import com.bicente44.simba.model.ActivityState
import com.bicente44.simba.model.Mood
import com.bicente44.simba.model.SimbaDefaults
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

private data class Sparkle(val id: Long, val position: Offset, val spawnTime: Long)

/**
 * This hold the core center Simba image, carries the ability
 * to detect dragging over the image and sets the sparkles.
 */
@Composable
fun PettableSimba(
    activityState: ActivityState,
    mood: Mood,
    isDead: Boolean,
    onPetTick: (Boolean) -> Unit,
    onPetEnded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var sparkles by remember { mutableStateOf(listOf<Sparkle>()) }

    Box(
        modifier = modifier.pointerInput(Unit) {
            if (isDead) return@pointerInput
            var dragStartTime = 0L
            var lastHappinessTick = 0L
            var lastSparkleSpawn = 0L

            detectDragGestures(
            onDragStart = {
                dragStartTime = System.currentTimeMillis()
                lastHappinessTick = dragStartTime
                onPetTick(false)
            },
            onDrag = { change, _ ->
                val now = System.currentTimeMillis()
                val pastWarmup = now - dragStartTime >= SimbaDefaults.PETTING_SPARKLE_WARMUP_MILLIS

                if (now - lastHappinessTick >= SimbaDefaults.PETTING_TICK_INTERVAL_MILLIS) {
                    onPetTick(pastWarmup)
                    lastHappinessTick = now
                }
                if (pastWarmup && now - lastSparkleSpawn >= SimbaDefaults.SPARKLE_SPAWN_INTERVAL_MILLIS) {
                    sparkles = sparkles + Sparkle(id = now, position = change.position, spawnTime = now)
                    lastSparkleSpawn = now
                }
            },
                onDragEnd = { onPetEnded() }
            )
        }
    ) {
        Image(painter = simbaPainterFor(activityState, mood, isDead), contentDescription = null)
        sparkles.forEach { sparkle ->
            key(sparkle.id) {
                SparkleParticle(position = sparkle.position) {
                    sparkles = sparkles.filterNot { it.id == sparkle.id }
                }
            }
        }
    }
}
@Composable
private fun SparkleParticle(position: Offset, onFinished: () -> Unit) {
    val alpha = remember { Animatable(1f) }
    val drift = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch { alpha.animateTo(0f, tween(SimbaDefaults.SPARKLE_LIFETIME_MILLIS)) }
        drift.animateTo(-40f, tween(SimbaDefaults.SPARKLE_LIFETIME_MILLIS))
        onFinished()
    }

    Canvas(
        modifier = Modifier
            .offset { IntOffset(position.x.toInt(), (position.y + drift.value).toInt()) }
            .size(16.dp)
            .alpha(alpha.value)
    ) {
        val c = center
        val radius = size.minDimension / 2
        val rayPairs = 4
        for (i in 0 until rayPairs) {
            val angle = Math.PI * i / rayPairs
            val dx = (radius * cos(angle)).toFloat()
            val dy = (radius * sin(angle)).toFloat()
            drawLine(Color.Yellow, Offset(c.x - dx, c.y - dy), Offset(c.x + dx, c.y + dy), strokeWidth = 3f)
        }
    }
}

/**
 * Helper function to call to paint the specific Simba.
 */
@Composable
fun simbaPainterFor(activity: ActivityState, mood: Mood, isDead: Boolean): Painter {
    if (isDead) return painterResource(R.drawable.simba_heaven)
    return when (activity) {
        ActivityState.PETTING -> painterResource(R.drawable.simba_crazy) //TODO: Get a better petting photo
        ActivityState.IDLE -> when (mood) {
            Mood.HAPPY -> painterResource(R.drawable.simba_curious_happy)
            Mood.SAD -> painterResource(R.drawable.simba_sad)
            Mood.TIRED -> painterResource(R.drawable.simba_tired)
            Mood.SICK -> painterResource(R.drawable.simba_sick)
            Mood.ANGRY -> painterResource(R.drawable.simba_angry)
            Mood.NEUTRAL -> painterResource(R.drawable.simba_idle)
        }
        ActivityState.EATING -> painterResource(R.drawable.simba_eating)
        ActivityState.PLAYING -> painterResource(R.drawable.simba_silly) // TODO: Get a better playing Simba photo
        ActivityState.SLEEPING -> painterResource(R.drawable.simba_sleep)
        ActivityState.GROOMING -> painterResource(R.drawable.simba_grooming)
    }
}