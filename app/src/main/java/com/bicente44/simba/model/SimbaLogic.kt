package com.bicente44.simba.model

/**
 * Calculate Simba's age, should be called periodically. It returns Simba's age in days.
 */
fun calculateAge(state: SimbaState, now: Long): Int {
    val elapsed = now - state.creationTimestamp
    return (elapsed / (1000 * 60 * 60 * 24)).toInt()
}

/**
 * Calculates the mood.
 * Checks positive stats first then lowest stats.
 */
fun calculateMood(state: SimbaState): Mood {
    return when {
        state.health <= 30 -> Mood.SICK
        state.energy <= 20 -> Mood.TIRED
        state.hunger <= 20 && state.cleanliness <= 20 -> Mood.ANGRY
        state.happiness <= 30 -> Mood.SAD
        state.happiness >= 70 && state.hunger >= 60 && state.cleanliness >= 60 -> Mood.HAPPY
        else -> Mood.NEUTRAL
    }
}

/**
 * Simba is dead when health hits zero.
 */
fun isDead(state: SimbaState): Boolean = state.health <= 0

/**
 * Applies decay after every tick, if no energy or hunger Simba loses HP.
 */
fun applyDecay(state: SimbaState, now: Long): SimbaState {
    if (isDead(state)) return state

    val elapsedMillis = now - state.lastSeenTimestamp
    val elapsed = elapsedMillis / (1000 * 60)

    val isOffline = elapsed > SimbaDefaults.OFFLINE_THRESHOLD_MINUTES
    val decayMultiplier = if (isOffline) SimbaDefaults.OFFLINE_DECAY_MULTIPLIER else 1.0

    val newHunger = (state.hunger - (SimbaDefaults.HUNGER_DECAY_PER_TICK * elapsed * decayMultiplier)).toInt().coerceIn(0, 100)
    val newEnergy = (state.energy - (SimbaDefaults.ENERGY_DECAY_PER_TICK * elapsed * decayMultiplier)).toInt().coerceIn(0, 100)
    val newCleanliness = (state.cleanliness - (SimbaDefaults.CLEAN_DECAY_PER_TICK * elapsed * decayMultiplier)).toInt().coerceIn(0, 100)

    val isHurting = newHunger == 0 || newEnergy == 0

    val happinessDecay = if (isHurting) SimbaDefaults.HAPPINESS_DECAY_SEVERE else SimbaDefaults.HAPPINESS_DECAY_PER_TICK
    val newHappiness = (state.happiness - (happinessDecay * elapsed * decayMultiplier)).toInt().coerceIn(0, 100)

    val isThriving = !isHurting &&
            newHunger > SimbaDefaults.THRIVING_STAT_THRESHOLD &&
            newEnergy > SimbaDefaults.THRIVING_STAT_THRESHOLD &&
            newCleanliness > SimbaDefaults.THRIVING_STAT_THRESHOLD &&
            newHappiness > SimbaDefaults.THRIVING_STAT_THRESHOLD

    val newHealth = when {
        isHurting -> (state.health - (SimbaDefaults.HEALTH_DECAY_PER_TICK * elapsed * decayMultiplier)).toInt().coerceIn(0, 100) // now correctly uses decayMultiplier
        isThriving -> (state.health + (SimbaDefaults.HEALTH_REGEN_PER_TICK * elapsed * decayMultiplier)).toInt().coerceIn(0, 100)
        else -> state.health
    }

    return state.copy(
        hunger = newHunger,
        energy = newEnergy,
        cleanliness = newCleanliness,
        happiness = newHappiness,
        health = newHealth,
        lastSeenTimestamp = now
    )
}

/**
 * This checks if you can click an action (cooldown)
 */
fun canPerformAnyAction(state: SimbaState, now: Long): Boolean {
    return now - state.lastActionTimestamp >= SimbaDefaults.ACTION_CLICK_COOLDOWN_MILLIS
}

/**
 * Feeds Simba. Specify how much he gets fed.
 * Side effect, also gain energy as Simba eats.
 */
fun applyFeed(
    state: SimbaState,
    now: Long,
    foodGain: Int,
    energyGain: Int,
    healthGain: Int
): SimbaState {
    return state.copy(
        hunger = (state.hunger + foodGain).coerceIn(0, 100),
        energy = (state.energy + energyGain).coerceIn(0, 100),
        health = (state.health + healthGain).coerceIn(0, 100),
        activityState = ActivityState.EATING,
        activityStartTimestamp = now
    )
}

/**
 * When you play with Simba he gains happiness.
 * Happy Simba, happy life
 */
fun applyPlay(
    state: SimbaState,
    now: Long,
    playGain: Int,
    energyLoss: Int,
): SimbaState {
    return state.copy(
        happiness = (state.happiness + playGain).coerceIn(0, 100),
        energy = (state.energy - energyLoss).coerceIn(0, 100),
        activityState = ActivityState.PLAYING,
        activityStartTimestamp = now
    )
}

/**
 * Simba eepy, sleep = energy
 */
fun applySleep(
    state: SimbaState,
    now: Long,
    sleepGain: Int,
    hungerLoss: Int,
    healthGain: Int
): SimbaState {
    return state.copy(
        energy = (state.energy + sleepGain).coerceIn(0, 100),
        hunger = (state.hunger - hungerLoss).coerceIn(0, 100),
        health = (state.health + healthGain).coerceIn(0, 100),
        activityState = ActivityState.SLEEPING,
        activityStartTimestamp = now
    )
}

/**
 * The endless self grooming cat cleans himself
 */
fun applyClean(state: SimbaState, cleanGain: Int, now: Long): SimbaState {
    return state.copy(
        cleanliness = (state.cleanliness + cleanGain).coerceIn(0, 100),
        activityState = ActivityState.GROOMING,
        activityStartTimestamp = now
    )
}