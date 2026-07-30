package com.bicente44.simba.model.internationalization

import com.bicente44.simba.model.Language

enum class IntroCutsceneStringKey {
    SCENE_1, SCENE_2, SCENE_3, SCENE_4, SCENE_5, SCENE_6, SCENE_7, SCENE_8, SCENE_9, SCENE_10, ADVANCE_HINT
}

object IntroCutsceneStrings {
    fun get(key: IntroCutsceneStringKey, language: Language): String = when (language) {
        Language.ENGLISH -> english(key)
        Language.FRENCH -> french(key)
    }

    private fun english(key: IntroCutsceneStringKey): String = when (key) {
        IntroCutsceneStringKey.SCENE_1 -> "On a walk around the neighbourhood, I almost didn't see him. Just a faint rustle in the grass, and a sound too weak to even be called a meow."
        IntroCutsceneStringKey.SCENE_2 -> "I walked away, figuring his mother must be nearby. I walked away and let nature take its course."
        IntroCutsceneStringKey.SCENE_3 -> "But the thought of him didn't leave my mind. When I returned the next day, he had barely moved since that spot. Left entirely on his own, he seemed very sick."
        IntroCutsceneStringKey.SCENE_4 -> "I knew if I walked away again, he wouldn't make it. I scooped him up, seeing just how sick, and fragile he really was."
        IntroCutsceneStringKey.SCENE_5 -> "The first weeks were many visits to the vet, lots of medicine and worry. It took a lot of time to fight off the sickness."
        IntroCutsceneStringKey.SCENE_6 -> "Slowly, the grime and disease started to wash away. He could finally see better and started to play around."
        IntroCutsceneStringKey.SCENE_7 -> "He started playing and having energy for the first time."
        IntroCutsceneStringKey.SCENE_8 -> "He knew he was safe with us, making sure we stayed with him by his side"
        IntroCutsceneStringKey.SCENE_9 -> "He was no longer a sick dying kitten and grew into a cute light orange cat."
        IntroCutsceneStringKey.SCENE_10 -> "Much time has passed, now I am passing Simba to you! I want you to take care of him and love him just as much as I've done."
        IntroCutsceneStringKey.ADVANCE_HINT -> "(HINT: Tap the image to view the next scene)"
    }

    private fun french(key: IntroCutsceneStringKey): String = when (key) {
        IntroCutsceneStringKey.SCENE_1 -> "Lors d'une promenade dans le quartier, j'ai failli ne pas le voir. Juste un léger bruissement dans l'herbe, et un son trop faible pour même être appelé un miaulement."
        IntroCutsceneStringKey.SCENE_2 -> "Je suis reparti, pensant que sa mère devait être tout près. Je suis reparti et j'ai laissé la nature suivre son cours."
        IntroCutsceneStringKey.SCENE_3 -> "Mais la pensée de lui ne me quittait pas. Quand je suis revenu le lendemain, il avait à peine bougé de cet endroit. Livré entièrement à lui-même, il semblait très malade."
        IntroCutsceneStringKey.SCENE_4 -> "Je savais que si je repartais encore une fois, il n'y survivrait pas. Je l'ai pris dans mes bras, réalisant à quel point il était malade et fragile."
        IntroCutsceneStringKey.SCENE_5 -> "Les premières semaines ont été remplies de visites chez le vétérinaire, de médicaments et d'inquiétude. Il a fallu beaucoup de temps pour vaincre la maladie."
        IntroCutsceneStringKey.SCENE_6 -> "Peu à peu, la saleté et la maladie ont commencé à disparaître. Il pouvait enfin mieux voir et a commencé à jouer."
        IntroCutsceneStringKey.SCENE_7 -> "Il a commencé à jouer et à avoir de l'énergie pour la première fois."
        IntroCutsceneStringKey.SCENE_8 -> "Il savait qu'il était en sécurité avec nous, s'assurant qu'on reste à ses côtés."
        IntroCutsceneStringKey.SCENE_9 -> "Il n'était plus ce chaton malade sur le point de mourir, et il est devenu un beau chat orange pâle."
        IntroCutsceneStringKey.SCENE_10 -> "Beaucoup de temps a passé, et maintenant, je te confie Simba ! Je veux que tu prennes soin de lui et que tu l'aimes autant que je l'ai fait."
        IntroCutsceneStringKey.ADVANCE_HINT -> "(ASTUCE : Touchez l'image pour voir la scène suivante)"
    }
}