package com.gkreduction.vocabulary.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "word_db", indices = [Index(value = ["text"], unique = true)])
data class WordDb(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    val text: String,
    val translate: String,
)
