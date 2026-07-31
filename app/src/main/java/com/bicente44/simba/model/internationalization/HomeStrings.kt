package com.bicente44.simba.model.internationalization

import com.bicente44.simba.model.Language

enum class HomeStringKey {
    RESTART_BUTTON,
    SPEECH_HAPPY,
    SPEECH_SAD,
    SPEECH_TIRED,
    SPEECH_SICK,
    SPEECH_ANGRY,
    SPEECH_NEUTRAL,
    SPEECH_EATING,
    SPEECH_PLAYING,
    SPEECH_SLEEPING,
    SPEECH_GROOMING,
    SPEECH_PETTING, }

object HomeStrings {
    fun get(key: HomeStringKey, language: Language): String = when (language) {
        Language.ENGLISH -> english(key)
        Language.FRENCH -> french(key)
    }
    private fun english(key: HomeStringKey): String = when (key) {
        HomeStringKey.RESTART_BUTTON -> "Restart"
        HomeStringKey.SPEECH_HAPPY -> "I'm so happy right now!"
        HomeStringKey.SPEECH_SAD -> "I'm feeling a bit down I want to play"
        HomeStringKey.SPEECH_TIRED -> "So eepy.. need a nap."
        HomeStringKey.SPEECH_SICK -> "I don't feel so good."
        HomeStringKey.SPEECH_ANGRY -> "*constant biting*"
        HomeStringKey.SPEECH_NEUTRAL -> "meow"
        HomeStringKey.SPEECH_EATING -> "Yum yum yum!"
        HomeStringKey.SPEECH_PLAYING -> "Woohoo!"
        HomeStringKey.SPEECH_SLEEPING -> "Zzz... zzz..."
        HomeStringKey.SPEECH_GROOMING -> "mlem mlem mlem mlem"
        HomeStringKey.SPEECH_PETTING -> "Purrrr..."
    }
    private fun french(key: HomeStringKey): String = when (key) {
        HomeStringKey.RESTART_BUTTON -> "Recommencer"
        HomeStringKey.SPEECH_HAPPY -> "Je suis tellement content !"
        HomeStringKey.SPEECH_SAD -> "Je me sens un peu triste je veux jouer"
        HomeStringKey.SPEECH_TIRED -> "J'ai tellement sommeil.. il me faut une sieste."
        HomeStringKey.SPEECH_SICK -> "Je ne me sens pas très bien."
        HomeStringKey.SPEECH_ANGRY -> "*en train de te mordre*"
        HomeStringKey.SPEECH_NEUTRAL -> "meow"
        HomeStringKey.SPEECH_EATING -> "Miam miam miam !"
        HomeStringKey.SPEECH_PLAYING -> "Woohoo!"
        HomeStringKey.SPEECH_SLEEPING -> "Zzz... zzz..."
        HomeStringKey.SPEECH_GROOMING -> "mlem mlem mlem mlem"
        HomeStringKey.SPEECH_PETTING -> "Ronron..."
    }

    fun ageLabel(age: Int, language: Language): String = when (language) {
        Language.ENGLISH -> "Age: $age"
        Language.FRENCH -> "Âge: $age"
    }
}