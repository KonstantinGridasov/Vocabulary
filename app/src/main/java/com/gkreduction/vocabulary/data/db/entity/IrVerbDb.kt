package com.gkreduction.vocabulary.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "ir_verb_db", indices = [Index(value = ["russian"], unique = true)])
data class IrVerbDb(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    val russian: String,
    @ColumnInfo(name = "form_one") val form1: String,
    @ColumnInfo(name = "form_two") val form2: String,
    @ColumnInfo(name = "form_three") val form3: String,

    )