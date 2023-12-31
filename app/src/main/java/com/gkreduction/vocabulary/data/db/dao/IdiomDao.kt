package com.gkreduction.vocabulary.data.db.dao

import androidx.room.*
import com.gkreduction.vocabulary.data.db.entity.IdiomDb

@Dao
interface IdiomDao {
    @Query("SELECT * FROM idiom_db")
    suspend fun getRandomListIdioms(): List<IdiomDb>?

    @Query("SELECT * FROM idiom_db ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomIdiom(): IdiomDb?

    @Transaction
    suspend fun saveIdioms(list: List<IdiomDb>) {
        list.forEach {
            saveIdiom(it)
        }
    }

    @Transaction
    suspend fun saveIdiom(idiom: IdiomDb) {
        if (isExistIdiom(idiom.russian)) {
            update(idiom)
        } else
            insert(idiom)
    }

    @Query("SELECT EXISTS(SELECT * FROM idiom_db WHERE russian LIKE :text)")
    suspend fun isExistIdiom(text: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idiom: IdiomDb): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(idiom: IdiomDb)

}