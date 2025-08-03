package com.example.quicknotes.data.data_classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val lastUpdated: Long = System.currentTimeMillis()
)
