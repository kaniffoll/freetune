package com.example.domain.audio.analyzer

interface AudioAnalyzer {
    fun startListening(onPitchDetected: (Float) -> Unit)
    fun endListening()
}