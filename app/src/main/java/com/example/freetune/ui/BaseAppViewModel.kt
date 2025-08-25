package com.example.freetune.ui

import androidx.lifecycle.ViewModel
import com.example.domain.audio.analyzer.AudioAnalyzer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseAppViewModel @Inject constructor(
    private val audioAnalyzer: AudioAnalyzer
) : ViewModel() {
    fun getCurrentPitch(): String {
        var currentPitch = 0F
        audioAnalyzer.startListening { pitch ->
            currentPitch = pitch
        }
        return currentPitch.toString()
    }
}