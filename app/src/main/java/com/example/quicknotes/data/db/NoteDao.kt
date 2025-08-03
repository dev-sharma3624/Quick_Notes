package com.example.quicknotes.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quicknotes.data.data_classes.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("""
        Select * from notes_table;
    """)
    fun select() : Flow<List<Note>>

    @Query("""
        Delete from notes_table;
    """)
    suspend fun deleteAll()

    @Delete
    suspend fun delete(note: Note)
}