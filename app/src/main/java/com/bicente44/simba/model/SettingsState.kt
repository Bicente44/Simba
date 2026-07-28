package com.bicente44.simba.model

import kotlinx.serialization.Serializable

/**
 * The state of the settings
 */
@Serializable
data class SettingsState(
    val musicEnabled: Boolean,
    val musicVolume: Float,
    val sfxEnabled: Boolean,
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