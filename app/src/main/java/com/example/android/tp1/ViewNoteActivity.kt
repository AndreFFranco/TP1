package com.example.android.tp1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.tp1.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.viewnote.*

class ViewNoteActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewnote)

        val id = intent.getIntExtra("id",0)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")

        val textViewTitle = findViewById<TextView>(R.id.titlenote)
        textViewTitle.text=title

        val textViewDesc = findViewById<TextView>(R.id.descnote)
        textViewDesc.text=description

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val edit = findViewById<ImageButton>(R.id.editbtn)
        edit.setOnClickListener {
            val newtitle = textViewTitle.text.toString()
            val newdesc = textViewDesc.text.toString()

            if (isNullOrEmpty(newtitle)|| isNullOrEmpty(newdesc) ) {
                Toast.makeText(
                        applicationContext,
                        R.string.editerror,
                        Toast.LENGTH_LONG).show()
            } else {
                noteViewModel.updateNote(id, newtitle, newdesc)
                finish()

            }
        }

        val apagar = findViewById<ImageButton>(R.id.deletebtn)
        apagar.setOnClickListener {
            val Alert = AlertDialog.Builder(this)
            Alert.setTitle(getString(R.string.alerttitle))
            Alert.setPositiveButton(getString(R.string.yesdelete)){ dialog: DialogInterface?, which: Int ->
                noteViewModel.deleteById(id)
                finish()
            }
            Alert.setNegativeButton(getString(R.string.nodelete)){ dialog, id ->
                dialog.dismiss()
            }
            Alert.show()
        }

    }

    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }

}
