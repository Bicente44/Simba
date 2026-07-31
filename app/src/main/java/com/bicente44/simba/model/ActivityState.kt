package com.bicente44.simba.model

import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
enum class ActivityState {
    IDLE,
    EATING,
    PLAYING,
    GROOMING,
    SLEEPING,
    PETTING,
}