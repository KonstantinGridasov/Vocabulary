package com.gkreduction.vocabulary.presentation.ui.main.fragment.exam

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkreduction.vocabulary.data.repository.db.DbRepository
import com.gkreduction.vocabulary.data.repository.shared.SharedRepository
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    var dbRepository: DbRepository,
    var sharedRepository: SharedRepository
) : ViewModel() {
    var baseWord = MutableLiveData<BaseWord>()


    fun getCardExam() {
        viewModelScope.launch {
            sharedRepository.getSettings().let {

                dbRepository.getBaseWordExam(it).let { word ->
                    if (word != null)
                        baseWord.value = word
                }

            }

        }
    }
}

