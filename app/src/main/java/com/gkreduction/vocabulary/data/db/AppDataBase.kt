package com.gkreduction.vocabulary.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gkreduction.vocabulary.data.db.dao.IdiomDao
import com.gkreduction.vocabulary.data.db.dao.IrVerbDao
import com.gkreduction.vocabulary.data.db.dao.WordDao
import com.gkreduction.vocabulary.data.db.entity.IdiomDb
import com.gkreduction.vocabulary.data.db.entity.IrVerbDb
import com.gkreduction.vocabulary.data.db.entity.WordDb

@Database(
    entities = [IdiomDb::class, IrVerbDb::class, WordDb::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun idiomDao(): IdiomDao
    abstract fun irVerDao(): IrVerbDao
    abstract fun wordDao(): WordDao

    companion object {

        private const val DB_NAME = "vocabulary-db"

        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java, DB_NAME
            ).build()
        }
    }

}