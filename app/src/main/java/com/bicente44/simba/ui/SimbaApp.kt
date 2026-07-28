package com.bicente44.simba.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bicente44.simba.ui.screen.Credits
import com.bicente44.simba.ui.screen.Extra
import com.bicente44.simba.viewmodel.SimbaViewModel
import com.bicente44.simba.ui.screen.Home
import com.bicente44.simba.ui.screen.IntroCutscene
import com.bicente44.simba.ui.screen.Settings

@Composable
fun SimbaApp(modifier: Modifier = Modifier) {
    val viewModel: SimbaViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    var currentScreen by remember {
        //mutableStateOf(if (state.hasSeenIntro) Screen.HOME else Screen.INTRO)
        mutableStateOf(Screen.HOME) // TODO: revert to hasSeenIntro check once Intro is built
    }

    fun goBack() {
        currentScreen = when (currentScreen) {
            Screen.EXTRAS -> Screen.SETTINGS
            Screen.CREDITS, Screen.INTRO -> Screen.EXTRAS
            else -> Screen.HOME
        }
    }

    fun dismissAll() { currentScreen = Screen.HOME }

    Box (contentAlignment = Alignment.Center)
    {
        Home(viewModel = viewModel, onSettingsClicked = { currentScreen = Screen.SETTINGS })

        when (currentScreen) {
            Screen.SETTINGS -> Settings(
                viewModel = viewModel,
                onBack = ::goBack,
                onDismissAll = ::dismissAll,
                onExtrasClicked = { currentScreen = Screen.EXTRAS }
            )
            Screen.CREDITS -> Credits(
                onBack = ::goBack,
                onDismissAll = ::dismissAll
            )
            Screen.EXTRAS -> Extra(
                onBack = ::goBack,
                onDismissAll = ::dismissAll,
                onCreditsClicked = { currentScreen = Screen.CREDITS },
                onRewatchIntroClicked = { currentScreen = Screen.INTRO },
                )
            Screen.INTRO -> IntroCutscene(
                onBack = ::goBack,
                onDismissAll = ::dismissAll
            )
            else -> {}
        }
    }
}