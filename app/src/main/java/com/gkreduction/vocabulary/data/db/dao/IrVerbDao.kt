package com.gkreduction.vocabulary.data.db.dao

import androidx.room.*
import com.gkreduction.vocabulary.data.db.entity.IrVerbDb

@Dao
interface IrVerbDao {
    @Query("SELECT * FROM ir_verb_db ")
    suspend fun getRandomListIrVerbs(): List<IrVerbDb>?

    @Query("SELECT * FROM ir_verb_db ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomIrVerb(): IrVerbDb?


    @Transaction
    suspend fun saveIrVerbs(list: List<IrVerbDb>) {
        list.forEach {
            saveIrVerb(it)
        }
    }

    @Transaction
    suspend fun saveIrVerb(irVerb: IrVerbDb) {
        if (isExistIrVerb(irVerb.russian)) {
            update(irVerb)
        } else
            insert(irVerb)
    }

    @Query("SELECT EXISTS(SELECT * FROM ir_verb_db WHERE russian LIKE :text)")
    suspend fun isExistIrVerb(text: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(irVerb: IrVerbDb): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(irVerb: IrVerbDb)

}