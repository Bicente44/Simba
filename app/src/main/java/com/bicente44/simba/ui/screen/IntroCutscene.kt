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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bicente44.simba.R
import com.bicente44.simba.model.internationalization.IntroCutsceneStringKey
import com.bicente44.simba.model.internationalization.IntroCutsceneStrings
import com.bicente44.simba.viewmodel.SimbaViewModel

private data class IntroScene(val imageRes: Int, val captionKey: IntroCutsceneStringKey)
private val introScenes = listOf(
    IntroScene(imageRes = R.drawable.scene_grass1, captionKey = IntroCutsceneStringKey.SCENE_1),
    IntroScene(imageRes = R.drawable.simba_grass2_leave, captionKey = IntroCutsceneStringKey.SCENE_2),
    IntroScene(imageRes = R.drawable.simba_scene3_comeback, captionKey = IntroCutsceneStringKey.SCENE_3),
    IntroScene(imageRes = R.drawable.simba_scene4_pickup, captionKey = IntroCutsceneStringKey.SCENE_4),
    IntroScene(imageRes = R.drawable.simba_scene5_care, captionKey = IntroCutsceneStringKey.SCENE_5),
    IntroScene(imageRes = R.drawable.simba_scene6_clean, captionKey = IntroCutsceneStringKey.SCENE_6),
    IntroScene(imageRes = R.drawable.simba_scene7_playful, captionKey = IntroCutsceneStringKey.SCENE_7),
    IntroScene(imageRes = R.drawable.simba_scene8_sleep_silly, captionKey = IntroCutsceneStringKey.SCENE_8),
    IntroScene(imageRes = R.drawable.simba_scene9_juvenile, captionKey = IntroCutsceneStringKey.SCENE_9),
    IntroScene(imageRes = R.drawable.simba_scene10_resting_adult, captionKey = IntroCutsceneStringKey.SCENE_10),
)
@Composable
fun IntroCutscene(
    viewModel: SimbaViewModel,
    onFinished: () -> Unit,
) {
    var currentSceneIndex by remember { mutableIntStateOf(0) }
    val settingsState by viewModel.settingsState.collectAsState()

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
                            painter = painterResource(R.drawable.icon_back),
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
                    text = IntroCutsceneStrings.get(introScenes[currentSceneIndex].captionKey, settingsState.language),
                    color = Color.Black,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(Modifier.weight(1f))
                if (currentSceneIndex < 1)
                    Text(
                        text = IntroCutsceneStrings.get(IntroCutsceneStringKey.ADVANCE_HINT, settingsState.language),
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Italic,
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