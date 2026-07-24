package com.bicente44.simba.model

/**
 * Initializes a Simba at the start. Source of truth for all constants
 */
object SimbaDefaults {
    // Initial Stats
    const val INITIAL_HUNGER = 100
    const val INITIAL_ENERGY = 100
    const val INITIAL_CLEANLINESS = 100
    const val INITIAL_HAPPINESS = 100
    const val INITIAL_HEALTH = 100

    // Max USES till cooldowns
    const val PLAY_MAX_USES = 3
    const val SLEEP_MAX_USES = 3
    const val FEED_MAX_USES = 3

    // OFFLINE
    const val OFFLINE_THRESHOLD_MINUTES = 15 // How many minutes till offline
    const val OFFLINE_DECAY_MULTIPLIER = 0.5 // Offline decay is gentler than online

    // DECAY
    const val HUNGER_DECAY_PER_TICK = 0.15
    const val ENERGY_DECAY_PER_TICK = 0.2
    const val CLEAN_DECAY_PER_TICK = 0.15
    const val HAPPINESS_DECAY_NORMAL = 0.05
    const val HAPPINESS_DECAY_SEVERE = 0.3
    const val HEALTH_DECAY_PER_TICK = 0.1

    // COOLDOWN times
    const val ACTIVITY_DURATION_MILLIS: Long = 3000L    // 3 seconds
    const val ACTION_CLICK_COOLDOWN_MILLIS: Long = 2000L// 2 seconds
    const val FEED_COOLDOWN_MILLIS: Long = 60000        // 1 minute
    const val PLAY_COOLDOWN_MILLIS: Long = 60000
    const val SLEEP_COOLDOWN_MILLIS: Long = 60000

    fun newSimba(now: Long): SimbaState = SimbaState(
        hunger = INITIAL_HUNGER,
        energy = INITIAL_ENERGY,
        cleanliness = INITIAL_CLEANLINESS,
        happiness = INITIAL_HAPPINESS,
        health = INITIAL_HEALTH,
        activityState = ActivityState.IDLE,
        activityStartTimestamp = now,
        lastActionTimestamp = now,
        creationTimestamp = now,
        lastSeenTimestamp = now,
        feedCooldown = ActionCooldown(usesRemaining = FEED_MAX_USES, cooldownEndTimestamp = null),
        playCooldown = ActionCooldown(usesRemaining = PLAY_MAX_USES, cooldownEndTimestamp = null),
        sleepCooldown = ActionCooldown(usesRemaining = SLEEP_MAX_USES, cooldownEndTimestamp = null),
        hasSeenIntro = false,
    )
}
