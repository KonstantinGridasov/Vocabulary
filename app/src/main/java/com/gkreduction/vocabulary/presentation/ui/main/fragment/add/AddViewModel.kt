package com.gkreduction.vocabulary.presentation.ui.main.fragment.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkreduction.vocabulary.data.repository.db.DbRepository
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import com.gkreduction.vocabulary.presentation.entity.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private var dbRepository: DbRepository
) : ViewModel() {
    var clear = MutableLiveData<Boolean>()
    var settings = MutableLiveData<Settings>()

    fun save(word: BaseWord?) {
        if (word != null)
            viewModelScope.launch {
                dbRepository.saveWord(word)
                clear.value = true
            }
    }


}

