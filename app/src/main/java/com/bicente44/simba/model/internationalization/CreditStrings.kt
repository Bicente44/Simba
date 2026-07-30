package com.bicente44.simba.model.internationalization

import com.bicente44.simba.model.Language

enum class CreditsStringKey { TITLE, DEVELOPER_LABEL, ART_DIRECTION_LABEL, VISIT_WORK_LABEL, GITHUB_BUTTON, PORTFOLIO_BUTTON }

object CreditsStrings {
    fun get(key: CreditsStringKey, language: Language): String = when (language) {
        Language.ENGLISH -> english(key)
        Language.FRENCH -> french(key)
    }
    private fun english(key: CreditsStringKey): String = when (key) {
        CreditsStringKey.TITLE -> "Credits"
        CreditsStringKey.DEVELOPER_LABEL -> "Developer"
        CreditsStringKey.ART_DIRECTION_LABEL -> "Art Direction"
        CreditsStringKey.VISIT_WORK_LABEL -> "Visit my other work"
        CreditsStringKey.GITHUB_BUTTON -> "My GitHub"
        CreditsStringKey.PORTFOLIO_BUTTON -> "My Portfolio"
    }
    private fun french(key: CreditsStringKey): String = when (key) {
        CreditsStringKey.TITLE -> "Crédits"
        CreditsStringKey.DEVELOPER_LABEL -> "Développeur"
        CreditsStringKey.ART_DIRECTION_LABEL -> "Direction artistique"
        CreditsStringKey.VISIT_WORK_LABEL -> "Découvrez mes autres projets"
        CreditsStringKey.GITHUB_BUTTON -> "Mon GitHub"
        CreditsStringKey.PORTFOLIO_BUTTON -> "Mon portfolio"
    }
}