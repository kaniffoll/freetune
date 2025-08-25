package com.example.domain.audio.analyzer

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor
import com.example.domain.audio.AndroidRecordInputStream
import com.example.domain.resources.AudioRecorder.BUFFER_SIZE
import com.example.domain.resources.AudioRecorder.OVERLAP
import com.example.domain.resources.AudioRecorder.SAMPLE_RATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AudioAnalyzerImpl : AudioAnalyzer {
    private var dispatcher: AudioDispatcher? = null
    private var job: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    override fun startListening(onPitchDetected: (Float) -> Unit) {
        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE.toInt(),
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            BUFFER_SIZE * 10
        )

        if (dispatcher != null) return

        dispatcher = AudioDispatcher(
            AndroidRecordInputStream(audioRecord),
            BUFFER_SIZE,
            OVERLAP
        )

        val handler = PitchDetectionHandler { result, _ ->
            if (result.pitch > 0) {
                onPitchDetected(result.pitch)
            }
        }

        dispatcher?.addAudioProcessor(
            PitchProcessor(
                PitchProcessor.PitchEstimationAlgorithm.YIN,
                SAMPLE_RATE,
                BUFFER_SIZE,
                handler
            )
        )

        job = scope.launch {
            dispatcher?.run()
        }
    }

    override fun endListening() {
        dispatcher?.stop()
        dispatcher = null
        job?.cancel()
        job = null
    }
}