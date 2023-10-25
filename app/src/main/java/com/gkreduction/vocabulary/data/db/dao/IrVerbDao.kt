package com.gkreduction.vocabulary.data.db.dao

import androidx.room.*
import com.gkreduction.vocabulary.data.db.entity.IdiomDb
import com.gkreduction.vocabulary.data.db.entity.IrVerbDb

@Dao
interface IrVerbDao {
    @Query("SELECT * FROM ir_verb_db ORDER BY RANDOM()")
    suspend fun getRandomIrVerb(): List<IrVerbDb>?

    @Transaction
    suspend fun saveIrVerbs(list: List<IrVerbDb>) {
        list.forEach {
            saveIrVerb(it)
        }
    }

    @Transaction
    suspend fun saveIrVerb(irVerb: IrVerbDb) {
        if (isExistIrVerb(irVerb.text)) {
            update(irVerb)
        } else
            insert(irVerb)
    }

    @Query("SELECT EXISTS(SELECT * FROM ir_verb_db WHERE text LIKE :text)")
    suspend fun isExistIrVerb(text: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(irVerb: IrVerbDb): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(irVerb: IrVerbDb)

}