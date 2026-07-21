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
 * Applies decay after every tick, if no energy or hunger Simba loses HP.
 */
fun applyDecay(state: SimbaState, now: Long): SimbaState {
    val elapsedMillis = now - state.lastSeenTimestamp
    val elapsed = elapsedMillis / (1000 * 60)

    val isOffline = elapsed > SimbaDefaults.OFFLINE_THRESHOLD_MINUTES
    val decayMultiplier = if (isOffline) SimbaDefaults.OFFLINE_DECAY_MULTIPLIER else 1.0

    val newHunger = (state.hunger - (SimbaDefaults.HUNGER_DECAY_PER_TICK * elapsed * decayMultiplier)).toInt().coerceIn(0, 100)
    val newEnergy = (state.energy - (SimbaDefaults.ENERGY_DECAY_PER_TICK * elapsed * decayMultiplier)).toInt().coerceIn(0, 100)
    val newCleanliness = (state.cleanliness - (SimbaDefaults.CLEAN_DECAY_PER_TICK * elapsed * decayMultiplier)).toInt().coerceIn(0, 100)

    val isHurting = newHunger == 0 || newEnergy == 0
    val happinessDecay = if (isHurting) SimbaDefaults.HAPPINESS_DECAY_SEVERE
    else SimbaDefaults.HAPPINESS_DECAY_NORMAL
    val newHappiness = (state.happiness - (happinessDecay * elapsed)).toInt().coerceIn(0, 100)

    val newHealth = if (isHurting) (state.health - (SimbaDefaults.HEALTH_DECAY_PER_TICK * elapsed)).toInt().coerceIn(0, 100)
    else state.health

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
 * Feeds Simba. Specify how much he gets fed.
 * Side effect, also gain energy as Simba eats.
 */
fun applyFeed(state: SimbaState, now: Long, foodGain: Int, energyGain: Int): SimbaState {
    return state.copy(
        hunger = (state.hunger + foodGain).coerceIn(0, 100),
        energy = (state.energy + energyGain).coerceIn(0, 100)
    )
}

/**
 * When you play with Simba he gains happiness.
 * Happy Simba, happy life
 * TODO: decay energy (remove energy here), as Simba plays he gets tired
 */
fun applyPlay(state: SimbaState, now: Long, playGain: Int): SimbaState {
    return state.copy(
        happiness = (state.happiness + playGain).coerceIn(0, 100),
    )
}

/**
 * Simba eepy, sleep = energy
 * TODO: decay food (remove feed here), as Simba sleeps he gets hungry
 */
fun applySleep(state: SimbaState, now: Long, sleepGain: Int): SimbaState {
    return state.copy(
        energy = (state.hunger + sleepGain).coerceIn(0, 100),
    )
}

/**
 * The endless self grooming cat cleans himself
 */
fun applyClean(state: SimbaState, cleanGain: Int): SimbaState {
    return state.copy(
        cleanliness = (state.cleanliness + cleanGain).coerceIn(0, 100),
    )
}