package com.gkreduction.vocabulary.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "idiom_db", indices = [Index(value = ["russian"], unique = true)])
data class IdiomDb(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    val russian: String,
    val translate: String,
)

