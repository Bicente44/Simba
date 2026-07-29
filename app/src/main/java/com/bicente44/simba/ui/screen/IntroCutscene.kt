package com.bicente44.simba.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bicente44.simba.R
import com.bicente44.simba.viewmodel.SimbaViewModel

private data class IntroScene(val imageRes: Int, val caption: String)
private val introScenes = listOf(
    IntroScene(imageRes =
        R.drawable.scene_grass1, caption = "On a walk around the neighbourhood, I almost didn't see him. " +
            "Just a faint rustle in the grass, and a sound too weak to even be called a meow."),
    IntroScene(imageRes =
        R.drawable.simba_grass2_leave, caption = "I walked away, figuring his mother must be nearby. " +
            "I walked away and let nature take its course."),
    IntroScene(imageRes =
        R.drawable.simba_scene3_comeback, caption = "But the thought of him didn't leave my mind. When I returned the next day, " +
        "he had barely moved since spot. Left entirely on his own, he seemed very sick."),
    IntroScene(imageRes =
        R.drawable.simba_scene4_pickup, caption = "I knew if I walked away again, he wouldn't make it. " +
            "I scooped him up, seeing just how sick, and fragile he really was."),
    IntroScene(imageRes =
        R.drawable.simba_scene5_care, caption = "The first weeks were many visits to the vet, lots of medicine and worry. " +
            "It took a lot of time to fight off the sickness."),
    IntroScene(imageRes =
        R.drawable.simba_scene6_clean, caption = "Slowly, the grime and disease started to wash away. " +
            "He could finally see better and started to play around."),
    IntroScene(imageRes =
        R.drawable.simba_scene7_playful, caption = "He started playing and having energy for the first time."),
    IntroScene(imageRes =
        R.drawable.simba_scene8_sleep_silly, caption = "He knew he was safe with us, making sure we stayed with him by his side"),
    IntroScene(imageRes =
        R.drawable.simba_scene9_juvenile, caption = "He was no longer a sick dying kitten and grew into a cute light orange cat."),
    IntroScene(imageRes =
        R.drawable.simba_scene10_resting_adult, caption = "Much time has passed, now I am passing Simba to you! I want you to" +
            " take care of him and love him just as much as I've done."),
)
@Composable
fun IntroCutscene(
    viewModel: SimbaViewModel,
    onFinished: () -> Unit,
) {
    var currentSceneIndex by remember { mutableIntStateOf(0) }

    fun goToPreviousScene() {
        if (currentSceneIndex > 0) currentSceneIndex--
    }

    fun advanceScene() {
        if (currentSceneIndex < introScenes.lastIndex) {
            currentSceneIndex++
        } else {
            viewModel.onIntroFinished()
            onFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f)
                .background(Color(0xFFFFFFFF), RoundedCornerShape(16.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top bar row
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = ::goToPreviousScene,
                        enabled = currentSceneIndex > 0,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.icon_settings),
                            contentDescription = "Previous scene",
                            alpha = if (currentSceneIndex > 0) 1f else 0.3f,
                        )
                    }

                    Text(
                        text = "${currentSceneIndex + 1}/${introScenes.size}",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Black
                    )
                }
                Spacer(Modifier.size(12.dp))
                // MAIN CUTSCENE CONTENT
                SceneImageButton(
                    onClick = ::advanceScene,
                    imageResId = introScenes[currentSceneIndex].imageRes
                )
                Text(
                    text = introScenes[currentSceneIndex].caption,
                    color = Color.Black,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun SceneImageButton(onClick: () -> Unit, imageResId: Int,) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(imageResId),
            contentDescription = "",
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentScale = ContentScale.Crop
        )
    }
}