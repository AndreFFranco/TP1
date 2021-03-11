package com.example.android.tp1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        recycler_view.adapter = LineAdapter(myList)
        recycler_view.layoutManager = LineLayoutManager(this)

    }

    fun insert(view: View) {
        myList.add(0, Note("teste"))
        recycler_view.adapter?.notifyDataSetChanged()
    }
}