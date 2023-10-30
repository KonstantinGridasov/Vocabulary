package com.gkreduction.vocabulary.presentation.ui.main.fragment.add

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gkreduction.vocabulary.data.repository.shared.SharedRepository
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import com.gkreduction.vocabulary.presentation.entity.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private var sharedRepository: SharedRepository
) : ViewModel() {
    var settings = MutableLiveData<Settings>()

    fun save(word: BaseWord?) {
        Log.d("SAVEWORD", word.toString())

    }


}

