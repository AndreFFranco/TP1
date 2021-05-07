package com.example.android.tp1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddNote : AppCompatActivity() {

    private lateinit var notetitle: EditText
    private lateinit var notedesc: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_nota)

        notetitle = findViewById(R.id.entertitle)
        notedesc = findViewById(R.id.enterdesc)

        val button = findViewById<Button>(R.id.insertnote)

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(notetitle.text) || TextUtils.isEmpty(notedesc.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = notetitle.text.toString()
                val desc = notedesc.text.toString()
                replyIntent.putExtra(EXTRA_REPLYTITLE, title)
                replyIntent.putExtra(EXTRA_REPLYDESC, desc)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        val buttonBack = findViewById<Button>(R.id.backbtn)
        buttonBack.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLYTITLE = "titlereply"
        const val EXTRA_REPLYDESC = "descriptionreply"
    }
}