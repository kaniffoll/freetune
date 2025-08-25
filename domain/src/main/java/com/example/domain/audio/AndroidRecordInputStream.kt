package com.example.domain.audio

import android.media.AudioRecord
import android.util.Log
import be.tarsos.dsp.io.TarsosDSPAudioFormat
import be.tarsos.dsp.io.TarsosDSPAudioInputStream
import com.example.domain.resources.AudioRecorder.CHANNELS
import com.example.domain.resources.AudioRecorder.SAMPLE_RATE
import com.example.domain.resources.AudioRecorder.SAMPLE_SIZE_IN_BITS

class AndroidRecordInputStream(private val audioRecord: AudioRecord) : TarsosDSPAudioInputStream {
    override fun skip(bytesToSkip: Long): Long = 0

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        val readBytes = audioRecord.read(b, off, len)
        return if (readBytes > 0) readBytes else -1
    }

    override fun close() {
        if (audioRecord.state == AudioRecord.STATE_INITIALIZED) {
            try {
                audioRecord.stop()
            } catch (e: Exception) {
                Log.e(e.toString(), "AAA")
            }
        }
        audioRecord.release()
    }

    override fun getFormat(): TarsosDSPAudioFormat? {
        return TarsosDSPAudioFormat(
            SAMPLE_RATE,
            SAMPLE_SIZE_IN_BITS,
            CHANNELS,
            true,
            false
        )
    }

    override fun getFrameLength(): Long = -1
}