package com.bicente44.simba.model

import kotlinx.serialization.Serializable

/**
 * What defines a good cat, this is what Simba is composed of
 */
@Serializable
data class SimbaState (
    val hunger: Int,
    val energy: Int,
    val cleanliness: Int,
    val happiness: Int,
    val health: Int,
    val creationTimestamp: Long,
    val lastSeenTimestamp: Long,
    val feedCooldown: ActionCooldown,
    val playCooldown: ActionCooldown,
    val sleepCooldown: ActionCooldown,
    val hasSeenIntro: Boolean,
)