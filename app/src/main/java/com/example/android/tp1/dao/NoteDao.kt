package com.example.android.tp1.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.tp1.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * from note_table ORDER BY title ASC")
    fun getAlphabetizedNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("DELETE FROM note_table WHERE id == :id")
    suspend fun deleteById(id: Int)
}