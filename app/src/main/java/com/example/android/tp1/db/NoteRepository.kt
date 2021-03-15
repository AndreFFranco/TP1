package com.example.android.tp1.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.tp1.dao.NoteDao
import com.example.android.tp1.entities.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAlphabetizedNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }
}
