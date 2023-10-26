package com.gkreduction.vocabulary.data.repository.shared

import com.gkreduction.vocabulary.data.repository.shared.datasource.SharedDataSource
import com.gkreduction.vocabulary.presentation.entity.Settings

class SharedRepositoryImpl(
    var source: SharedDataSource
) : SharedRepository {


    override suspend fun saveSettings(settings: Settings): Settings {
        return source.saveSettings(settings)
    }

    override suspend fun getSettings(): Settings {
        return source.getSettings()
    }
}


