package com.example.android.tp1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.adapter.NoteAdapter
import com.example.android.tp1.entities.Note
import com.example.android.tp1.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotesActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private  var newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)

        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = NoteAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer { notes ->
            notes?.let { adapter.setNotes(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.insertbtn)
        fab.setOnClickListener {
            val intent = Intent(this@NotesActivity, AddNote::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddNote.EXTRA_REPLY)?.let {
                val note = Note(title = it, description = "Teste")
                noteViewModel.insert(note)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "@string/insertnote",
                Toast.LENGTH_SHORT).show()
        }
    }



}