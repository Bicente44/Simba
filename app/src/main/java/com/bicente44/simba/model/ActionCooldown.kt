package com.bicente44.simba.model

data class ActionCooldown(
    val usesRemaining: Int,
    val cooldownEndTimestamp: Long? = null
)