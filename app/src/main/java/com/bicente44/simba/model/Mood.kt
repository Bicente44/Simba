package com.bicente44.simba.model

import kotlinx.serialization.Serializable

/**
 * Simba's mood list, Calculated based on his stats (SimbaState)
 */
@Serializable
enum class Mood {
    HAPPY,
    SAD,
    TIRED,
    SICK,
    ANGRY,
    NEUTRAL
}