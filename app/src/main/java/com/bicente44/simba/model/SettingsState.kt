package com.bicente44.simba.model

import kotlinx.serialization.Serializable

/**
 * The state of the settings
 */
@Serializable
data class SettingsState(
    val musicVolume: Float,
    val sfxVolume: Float,
    val language: Language
)

/**
 * Language enum list for internationalization
 */
@Serializable
enum class Language {
    ENGLISH, FRENCH
}

val Language.displayName: String
    get() = when (this) {
        Language.ENGLISH -> "English"
        Language.FRENCH -> "Français"
    }