package com.example.quicknotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quicknotes.data.data_classes.Note

@Database(entities = [Note::class],
    version = 1)
abstract class AppDb : RoomDatabase(){
    abstract fun noteDao() : NoteDao
}