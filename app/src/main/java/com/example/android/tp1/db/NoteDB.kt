package com.example.android.tp1.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.tp1.dao.NoteDao
import com.example.android.tp1.entities.Note
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KParameter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = arrayOf(Note::class), version = 4, exportSchema = false)
public abstract class NoteDB : RoomDatabase() {

    abstract  fun noteDao(): NoteDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            /*
            KParameter.Kind.INSTANCE?.let { database ->
                scope.launch {
                    var noteDao = database.noteDao()

                    // Delete all content here.
                    noteDao.deleteAll()

                    // Add sample words.
                    var note = Note(1, "Titulo1", "Buraco na rua")
                    noteDao.insert(note)
                    note = Note(2, "Titulo2", "Buraco na outra rua")
                    noteDao.insert(note)
                }
            }*/
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NoteDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    "notes_database",
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()

                    INSTANCE = instance
                    return instance
            }
        }
    }

}