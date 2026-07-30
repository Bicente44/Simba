package com.bicente44.simba.model.internationalization

import com.bicente44.simba.model.Language

enum class ExtraStringKey { TITLE, CREDITS_BUTTON, REWATCH_INTRO_BUTTON }

object ExtraStrings {
    fun get(key: ExtraStringKey, language: Language): String = when (language) {
        Language.ENGLISH -> english(key)
        Language.FRENCH -> french(key)
    }
    private fun english(key: ExtraStringKey): String = when (key) {
        ExtraStringKey.TITLE -> "Extras"
        ExtraStringKey.CREDITS_BUTTON -> "Credits"
        ExtraStringKey.REWATCH_INTRO_BUTTON -> "Rewatch Intro"
    }
    private fun french(key: ExtraStringKey): String = when (key) {
        ExtraStringKey.TITLE -> "Extras"
        ExtraStringKey.CREDITS_BUTTON -> "Crédits"
        ExtraStringKey.REWATCH_INTRO_BUTTON -> "Revoir l'intro"
    }
}