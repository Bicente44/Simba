package com.bicente44.simba.model.internationalization

import com.bicente44.simba.model.Language

enum class HomeStringKey { RESTART_BUTTON }

object HomeStrings {
    fun get(key: HomeStringKey, language: Language): String = when (language) {
        Language.ENGLISH -> english(key)
        Language.FRENCH -> french(key)
    }
    private fun english(key: HomeStringKey): String = when (key) {
        HomeStringKey.RESTART_BUTTON -> "Restart"
    }
    private fun french(key: HomeStringKey): String = when (key) {
        HomeStringKey.RESTART_BUTTON -> "Recommencer"
    }

    fun ageLabel(age: Int, language: Language): String = when (language) {
        Language.ENGLISH -> "Age: $age"
        Language.FRENCH -> "Âge: $age"
    }
}