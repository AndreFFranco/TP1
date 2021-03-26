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

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    suspend fun deleteById(id: Int) {
        noteDao.deleteById(id)
    }

}
