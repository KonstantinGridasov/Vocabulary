package com.gkreduction.vocabulary.data.repository

import com.gkreduction.vocabulary.presentation.entity.BaseWord

interface DbRepository {
    suspend fun getWords(): List<BaseWord>
    suspend fun saveWords(list: List<BaseWord>)
    suspend fun saveWord(item: BaseWord)
}