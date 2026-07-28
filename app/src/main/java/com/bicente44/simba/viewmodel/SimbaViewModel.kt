package com.bicente44.simba.viewmodel

import com.russhwolf.settings.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bicente44.simba.model.ActionCooldown
import com.bicente44.simba.model.ActivityState
import com.bicente44.simba.model.Language
import com.bicente44.simba.model.SettingsState
import com.bicente44.simba.model.SimbaDefaults
import com.bicente44.simba.model.SimbaState
import com.bicente44.simba.model.applyClean
import com.bicente44.simba.model.applyDecay
import com.bicente44.simba.model.applyFeed
import com.bicente44.simba.model.applyPlay
import com.bicente44.simba.model.applySleep
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    private val _settingsState = MutableStateFlow(loadSettings())
    /**
     * The READ only state, this one is public to the composes.
     */
    val state: StateFlow<SimbaState> = _state.asStateFlow()
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    /**
     * Initialize on app launch. Applies state. Also starts decay timed thread.
     */
    init {
        val decayed = applyDecay(_state.value, System.currentTimeMillis())
        _state.value = decayed
        saveState(decayed)

        viewModelScope.launch {
            while (true) {
                delay(5 * 60 * 1000L) // 5 minutes in millis
                val decayed = applyDecay(_state.value, System.currentTimeMillis())
                _state.value = decayed
                saveState(decayed)
            }
        }
        viewModelScope.launch {
            while (true) {
                delay(200) // check every 0.2 seconds
                val current = _state.value
                if (current.activityState != ActivityState.IDLE &&
                    System.currentTimeMillis() - current.activityStartTimestamp >= SimbaDefaults.ACTIVITY_DURATION_MILLIS
                ) {
                    val reverted = current.copy(activityState = ActivityState.IDLE)
                    _state.value = reverted
                    saveState(reverted)
                }
            }
        }
    }

    /**
     * Loads Simba's state from memory
     * TODO: Simba app version, what if were updating major Simba state, cant just clear everything
     * TODO: Issue is we don't want someone to lose all their Simba age progress for an update.
     */
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
            val newState = fed.copy(feedCooldown = updatedCooldown, lastActionTimestamp = now)
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
            val newState = played.copy(playCooldown = updatedCooldown, lastActionTimestamp = now)
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
            val newState = slept.copy(sleepCooldown = updatedCooldown, lastActionTimestamp = now)
            _state.value = newState
            saveState(newState)
        }
    }

    /**
     * Applies amount of clean to stats, no cooldown (only stat no cooldown)
     */
    fun onCleanClicked(cleanGain: Int) {
        // same pattern, no cooldown check needed
        val now = System.currentTimeMillis()
        val cleaned = applyClean(_state.value, cleanGain, now)
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

    private fun loadSettings(): SettingsState {
        val save = settings.getStringOrNull("simba_settings")
        return if (save == null) {
            SimbaDefaults.defaultSettings()
        } else {
            Json.decodeFromString<SettingsState>(save)
        }
    }

    private fun saveSettings(newSettings: SettingsState) {
        settings.putString("simba_settings", Json.encodeToString(newSettings))
    }

    fun onMusicToggled(enabled: Boolean) {
        val updated = _settingsState.value.copy(musicEnabled = enabled)
        _settingsState.value = updated
        saveSettings(updated)
    }

    fun onMusicVolumeChanged(volume: Float) {
        val updated = _settingsState.value.copy(musicVolume = volume.coerceIn(0f, 1f))
        _settingsState.value = updated
        saveSettings(updated)
    }

    fun onSfxToggled(enabled: Boolean) {
        val updated = _settingsState.value.copy(sfxEnabled = enabled)
        _settingsState.value = updated
        saveSettings(updated)
    }

    fun onSfxVolumeChanged(volume: Float) {
        val updated = _settingsState.value.copy(sfxVolume = volume.coerceIn(0f, 1f))
        _settingsState.value = updated
        saveSettings(updated)
    }

    fun onLanguageChanged(language: Language) {
        val updated = _settingsState.value.copy(language = language)
        _settingsState.value = updated
        saveSettings(updated)
    }

}