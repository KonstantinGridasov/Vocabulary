package com.gkreduction.vocabulary.data.db.dao

import androidx.room.*
import com.gkreduction.vocabulary.data.db.entity.WordDb

@Dao
interface WordDao {
    @Query("SELECT * FROM word_db")
    suspend fun getRandomListWords(): List<WordDb>?


    @Query("SELECT * FROM word_db ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWord(): WordDb?

    @Transaction
    suspend fun saveWords(list: List<WordDb>) {
        list.forEach {
            saveWord(it)
        }
    }

    @Transaction
    suspend fun saveWord(word: WordDb) {
        if (isExistWord(word.russian)) {
            update(word)
        } else
            insert(word)
    }

    @Query("SELECT EXISTS(SELECT * FROM word_db WHERE russian LIKE :text)")
    suspend fun isExistWord(text: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordDb): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(word: WordDb)

}