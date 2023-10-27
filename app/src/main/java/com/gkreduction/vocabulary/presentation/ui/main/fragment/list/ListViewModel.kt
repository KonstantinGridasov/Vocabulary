package com.gkreduction.vocabulary.presentation.ui.main.fragment.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkreduction.vocabulary.data.repository.db.DbRepository
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private var dbRepository: DbRepository
) : ViewModel() {

    var listWord = MutableLiveData<List<BaseWord>>()

    fun getWord() {
        viewModelScope.launch {
            dbRepository.getWords().let {
                listWord.value = it
            }
        }
    }

    fun getIdiom() {
        viewModelScope.launch {
            dbRepository.getIdioms().let {
                listWord.value = it
            }
        }
    }

    fun getIrVerb() {
        viewModelScope.launch {
            dbRepository.getIrregularVerb().let {
                listWord.value = it
            }
        }
    }
}

