package com.gkreduction.vocabulary.data.repository.shared

import com.gkreduction.vocabulary.presentation.entity.Settings

interface SharedRepository {
    suspend fun saveSettings(settings: Settings): Settings
    suspend fun getSettings(): Settings
}