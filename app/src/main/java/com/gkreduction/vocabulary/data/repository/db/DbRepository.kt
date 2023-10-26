package com.gkreduction.vocabulary.data.repository.db

import com.gkreduction.vocabulary.presentation.entity.BaseWord
import com.gkreduction.vocabulary.presentation.entity.Settings

interface DbRepository {
    suspend fun getWords(): List<BaseWord>
    suspend fun saveWords(list: List<BaseWord>)
    suspend fun saveWord(item: BaseWord)
    suspend fun getBaseWordExam(settings: Settings): BaseWord?
}