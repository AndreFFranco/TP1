package com.example.android.tp1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.adapter.LineAdapter
import com.example.android.tp1.dataclasses.Note
import java.nio.file.attribute.AttributeView

class NotesActivity : AppCompatActivity() {

    private lateinit var myList: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)

        myList = ArrayList<Note>()

        for (i in 0 until 500) {
            myList.add(Note("title $i"))
        }

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


        recyclerview.adapter = LineAdapter(myList)
        recyclerview.layoutManager = LineLayoutManager(this)

    }

    fun insert(view: View) {
        myList.add(0, Note("teste"))
        recyclerview.adapter?.notifyDataSetChanged()
    }
}