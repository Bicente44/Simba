package com.bicente44.simba.model

/**
 * Initializes a Simba at the start. Source of truth for all constants
 */
object SimbaDefaults {
    const val INITIAL_HUNGER = 100
    const val INITIAL_ENERGY = 100
    const val FEED_HUNGER_GAIN = 20
    const val PLAY_MAX_USES = 3
    const val PLAY_COOLDOWN_MINUTES = 15

    /*fun newSimba(now: Long): SimbaState = SimbaState(
        hunger = INITIAL_HUNGER,
        energy = INITIAL_ENERGY,
        // ...
        creationTimestamp = now,
        lastSeenTimestamp = now,
        feedCooldown = ActionCooldown(usesRemaining = Int.MAX_VALUE), // unlimited if you kept feed uncapped, or capped
        playCooldown = ActionCooldown(usesRemaining = PLAY_MAX_USES),
        sleepCooldown = ActionCooldown(usesRemaining = SLEEP_MAX_USES)
    )
    */
}
