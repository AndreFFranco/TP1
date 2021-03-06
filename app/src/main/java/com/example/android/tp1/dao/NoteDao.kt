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

    @Query("UPDATE  note_table SET title = :title , description = :descripton where id = :id")
    suspend fun updateNote(id:Int, title :String, descripton: String)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("DELETE FROM note_table WHERE id == :id")
    suspend fun deleteById(id: Int)
}