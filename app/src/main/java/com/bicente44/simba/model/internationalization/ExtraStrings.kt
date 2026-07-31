package com.bicente44.simba.model.internationalization

import com.bicente44.simba.model.Language

enum class ExtraStringKey { TITLE, CREDITS_BUTTON, REWATCH_INTRO_BUTTON, GALLERY_BUTTON, GALLERY_TITLE }

object ExtraStrings {
    fun get(key: ExtraStringKey, language: Language): String = when (language) {
        Language.ENGLISH -> english(key)
        Language.FRENCH -> french(key)
    }
    private fun english(key: ExtraStringKey): String = when (key) {
        ExtraStringKey.TITLE -> "Extras"
        ExtraStringKey.CREDITS_BUTTON -> "Credits"
        ExtraStringKey.REWATCH_INTRO_BUTTON -> "Rewatch Intro"
        ExtraStringKey.GALLERY_BUTTON -> "Gallery"
        ExtraStringKey.GALLERY_TITLE -> "Gallery"
    }
    private fun french(key: ExtraStringKey): String = when (key) {
        ExtraStringKey.TITLE -> "Extras"
        ExtraStringKey.CREDITS_BUTTON -> "Crédits"
        ExtraStringKey.REWATCH_INTRO_BUTTON -> "Revoir l'intro"
        ExtraStringKey.GALLERY_BUTTON -> "Galerie"
        ExtraStringKey.GALLERY_TITLE -> "Galerie"
    }
}