package com.gkreduction.vocabulary.data.db.dao

import androidx.room.*
import com.gkreduction.vocabulary.data.db.entity.WordDb

@Dao
interface WordDao {
    @Query("SELECT * FROM word_db ORDER BY RANDOM()")
    suspend fun getRandomWord(): List<WordDb>?

    @Transaction
    suspend fun saveWords(list: List<WordDb>) {
        list.forEach {
            saveWord(it)
        }
    }

    @Transaction
    suspend fun saveWord(word: WordDb) {
        if (isExistWord(word.text)) {
            update(word)
        } else
            insert(word)
    }

    @Query("SELECT EXISTS(SELECT * FROM word_db WHERE text LIKE :text)")
    suspend fun isExistWord(text: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordDb): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(word: WordDb)

}