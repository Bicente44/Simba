package com.bicente44.simba.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bicente44.simba.R
import com.bicente44.simba.model.internationalization.ExtraStringKey
import com.bicente44.simba.model.internationalization.ExtraStrings
import com.bicente44.simba.ui.components.Overlay
import com.bicente44.simba.viewmodel.SimbaViewModel

@Composable
fun Gallery(
    viewModel: SimbaViewModel,
    onBack: () -> Unit,
    onDismissAll: () -> Unit,
) {
    val settingsState by viewModel.settingsState.collectAsState()

    Overlay(title = ExtraStrings.get(ExtraStringKey.GALLERY_BUTTON, settingsState.language), onBack = onBack, onDismissAll = onDismissAll) {

        val images = listOf(
            R.drawable.simba_heaven,
            R.drawable.simba_angry,
            R.drawable.simba_sad,
            R.drawable.simba_tired,
            R.drawable.simba_scene6_clean,
            R.drawable.simba_idle,
            R.drawable.simba_sick,
            R.drawable.simba_eating,
            R.drawable.simba_silly,
            R.drawable.simba_sniff,
            R.drawable.simba_crazy,
            R.drawable.simba_eepy_cute,
            R.drawable.simba_watah,
            R.drawable.simba_lucas
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            images.forEach { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
}