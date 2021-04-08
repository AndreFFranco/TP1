package com.example.android.tp1.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.tp1.dao.NoteDao
import com.example.android.tp1.entities.Note
import java.sql.RowId

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAlphabetizedNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun updateNote(id:Int, title :String, description: String) {
        noteDao.updateNote(id, title, description)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    suspend fun deleteById(id: Int) {
        noteDao.deleteById(id)
    }

}
