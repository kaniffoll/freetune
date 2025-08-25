package com.example.domain.di

import com.example.domain.audio.analyzer.AudioAnalyzer
import com.example.domain.audio.analyzer.AudioAnalyzerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AudioAnalyzerModule {

    @Provides
    @Singleton
    fun provideAudioAnalyzer(): AudioAnalyzer = AudioAnalyzerImpl()
}