package com.gkreduction.vocabulary.presentation.ui.main.fragment.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkreduction.vocabulary.data.repository.shared.SharedRepository
import com.gkreduction.vocabulary.presentation.entity.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private var sharedRepository: SharedRepository
) : ViewModel() {
    var settings = MutableLiveData<Settings>()

    fun getSettings() {
        viewModelScope.launch {
            sharedRepository.getSettings().let {
                settings.value = it

            }

        }
    }

    fun saveSettings(settings: Settings) {
        viewModelScope.launch {
            sharedRepository.saveSettings(settings)

        }
    }


}

