package com.bicente44.simba.model

import kotlinx.serialization.Serializable

@Serializable
data class ActionCooldown(
    val usesRemaining: Int,
    val cooldownEndTimestamp: Long? = null
) {
    fun canUse(now: Long): Boolean {
        return (usesRemaining > 0 || (cooldownEndTimestamp != null && now >= cooldownEndTimestamp))
    }

    fun afterUse(now: Long, maxUses: Int, cooldownDurationMillis: Long): ActionCooldown {
        val cooldownExpired = cooldownEndTimestamp != null && now >= cooldownEndTimestamp

        val startingUses = if (cooldownExpired) maxUses else usesRemaining
        val newUsesRemaining = (startingUses - 1).coerceIn(0, maxUses)

        val newCooldownTimestamp = if (newUsesRemaining == 0) {
            now + cooldownDurationMillis
        } else {
            null
        }

        return copy(
            usesRemaining = newUsesRemaining,
            cooldownEndTimestamp = newCooldownTimestamp
        )
    }
}