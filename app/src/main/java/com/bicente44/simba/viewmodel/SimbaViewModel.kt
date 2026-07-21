package com.bicente44.simba.viewmodel

import com.russhwolf.settings.Settings
import androidx.lifecycle.ViewModel
import com.bicente44.simba.model.ActionCooldown
import com.bicente44.simba.model.SimbaDefaults
import com.bicente44.simba.model.SimbaState
import com.bicente44.simba.model.applyClean
import com.bicente44.simba.model.applyDecay
import com.bicente44.simba.model.applyFeed
import com.bicente44.simba.model.applyPlay
import com.bicente44.simba.model.applySleep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A ViewModel for the Simba app, currently handles business coordination + persistence calls.
 */
class SimbaViewModel () : ViewModel() {
    private val settings: Settings = Settings()

    /**
     * Mutable state, when ViewModel does an action that modifies the state.
     */
    private val _state = MutableStateFlow(loadState())
    /**
     * The READ only state, this one is public to the composes.
     */
    val state: StateFlow<SimbaState> = _state.asStateFlow()

    /**
     * Initialize on app launch. Applies state.
     */
    init {
        val decayed = applyDecay(_state.value, System.currentTimeMillis())
        _state.value = decayed
        saveState(decayed)
    }

    private fun loadState(): SimbaState {
        // read from settings, decode JSON, or fall back to SimbaDefaults.newSimba()
        val save = settings.getStringOrNull("simba_save")
        return if (save == null) {
            SimbaDefaults.newSimba(System.currentTimeMillis())
        } else {
            Json.decodeFromString<SimbaState>(save)
        }
    }

    private fun saveState(newState: SimbaState) {
        // encode to JSON, write to settings
        settings.putString("simba_save", Json.encodeToString(newState))
    }

    /**
     * Applies amount of food to stats if the cooldown allows it.
     */
    fun onFeedClicked(foodGain: Int, energyGain: Int) {
        val now = System.currentTimeMillis()
        val current = _state.value

        if (current.feedCooldown.canUse(now)) {
            val fed = applyFeed(current, now, foodGain, energyGain)
            val updatedCooldown = current.feedCooldown.afterUse(now, SimbaDefaults.FEED_MAX_USES,
                SimbaDefaults.FEED_COOLDOWN_MILLIS)
            val newState = fed.copy(feedCooldown = updatedCooldown)
            _state.value = newState
            saveState(newState)
        }
    }

    /**
     * Applies amount of play (happiness) to stats if the cooldown allows it.
     */
    fun onPlayClicked(playGain: Int) {
        val now = System.currentTimeMillis()
        val current = _state.value

        if (current.playCooldown.canUse(now)) {
            val played = applyPlay(current, now, playGain)
            val updatedCooldown = current.playCooldown.afterUse(now, SimbaDefaults.PLAY_MAX_USES,
                SimbaDefaults.PLAY_COOLDOWN_MILLIS)
            val newState = played.copy(playCooldown = updatedCooldown)
            _state.value = newState
            saveState(newState)
        }
    }

    /**
     * Applies amount of sleep (energy) to stats if the cooldown allows it.
     */
    fun onSleepClicked(sleepGain: Int) {
        val now = System.currentTimeMillis()
        val current = _state.value

        if (current.sleepCooldown.canUse(now)) {
            val slept = applySleep(current, now, sleepGain)
            val updatedCooldown = current.sleepCooldown.afterUse(now, SimbaDefaults.SLEEP_MAX_USES,
                SimbaDefaults.SLEEP_COOLDOWN_MILLIS)
            val newState = slept.copy(sleepCooldown = updatedCooldown)
            _state.value = newState
            saveState(newState)
        }
    }

    /**
     * Applies amount of clean to stats, no cooldown (only stat no cooldown)
     */
    fun onCleanClicked(cleanGain: Int) {
        // same pattern, no cooldown check needed
        val cleaned = applyClean(_state.value, cleanGain)
        _state.value = cleaned
        saveState(cleaned)
    }

    /**
     * Function to mark intro seen (for first time login)
     */
    fun onIntroFinished() {
        val updated = _state.value.copy(hasSeenIntro = true)
        _state.value = updated
        saveState(updated)
    }
}