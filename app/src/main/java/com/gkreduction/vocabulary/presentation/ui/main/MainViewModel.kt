package com.gkreduction.vocabulary.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkreduction.vocabulary.data.repository.db.DbRepository
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var dbRepository: DbRepository) : ViewModel() {

    fun saveToDb(list: List<BaseWord>) {
        viewModelScope.launch {
            dbRepository.saveWords(list)
        }
    }
}