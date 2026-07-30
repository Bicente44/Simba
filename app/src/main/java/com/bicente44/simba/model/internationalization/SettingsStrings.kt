package com.bicente44.simba.model.internationalization

import com.bicente44.simba.model.Language

enum class SettingsStringKey { TITLE, AUDIO_SFX_HEADER, MUSIC_VOLUME_LABEL, SFX_VOLUME_LABEL, EXTRAS_BUTTON }

object SettingsStrings {
    fun get(key: SettingsStringKey, language: Language): String = when (language) {
        Language.ENGLISH -> english(key)
        Language.FRENCH -> french(key)
    }
    private fun english(key: SettingsStringKey): String = when (key) {
        SettingsStringKey.TITLE -> "Settings"
        SettingsStringKey.AUDIO_SFX_HEADER -> "Audio & SFX"
        SettingsStringKey.MUSIC_VOLUME_LABEL -> "Main"
        SettingsStringKey.SFX_VOLUME_LABEL -> "SFX"
        SettingsStringKey.EXTRAS_BUTTON -> "Extras"
    }
    private fun french(key: SettingsStringKey): String = when (key) {
        SettingsStringKey.TITLE -> "Paramètres"
        SettingsStringKey.AUDIO_SFX_HEADER -> "Audio et effets sonores"
        SettingsStringKey.MUSIC_VOLUME_LABEL -> "Principal"
        SettingsStringKey.SFX_VOLUME_LABEL -> "SFX"
        SettingsStringKey.EXTRAS_BUTTON -> "Extras"
    }
}