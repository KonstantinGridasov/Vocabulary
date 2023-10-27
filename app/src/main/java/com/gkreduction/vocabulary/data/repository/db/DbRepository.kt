package com.gkreduction.vocabulary.data.repository.db

import com.gkreduction.vocabulary.presentation.entity.BaseWord
import com.gkreduction.vocabulary.presentation.entity.Settings

interface DbRepository {
    suspend fun getFullDb(): List<BaseWord>
    suspend fun saveWords(list: List<BaseWord>)
    suspend fun saveWord(item: BaseWord)
    suspend fun getBaseWordExam(settings: Settings): BaseWord?

    suspend fun getWords(): List<BaseWord>
    suspend fun getIrregularVerb(): List<BaseWord>
    suspend fun getIdioms(): List<BaseWord>

}