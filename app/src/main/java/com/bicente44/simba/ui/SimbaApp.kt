package com.bicente44.simba.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bicente44.simba.viewmodel.SimbaViewModel
import com.bicente44.simba.ui.screen.Credits
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

    when (currentScreen) {
        Screen.INTRO -> {
            IntroCutscene(onFinished = {
                viewModel.onIntroFinished()
                currentScreen = Screen.HOME
            })
        }
        Screen.HOME -> {
            Home(viewModel = viewModel, onSettingsClicked = { currentScreen = Screen.SETTINGS })
        }
        Screen.SETTINGS -> {
            Settings(onBackClicked = { currentScreen = Screen.HOME }, onCreditsClicked = { currentScreen = Screen.CREDITS })
        }
        Screen.CREDITS -> {
            Credits(onBackClicked = { currentScreen = Screen.SETTINGS })
        }
    }
}