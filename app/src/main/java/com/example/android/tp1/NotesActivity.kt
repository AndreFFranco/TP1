package com.example.android.tp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.tp1.adapter.LineAdapter
import com.example.android.tp1.dataclasses.Notes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.notes.*


class NotesActivity : AppCompatActivity() {

    private lateinit var myList: ArrayList<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)

        myList = ArrayList<Notes>()

        for (i in 0 until 500) {
            myList.add(Notes("$i"))
        }

        recycler_view.adapter = LineAdapter(myList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.setHasFixedSize(true)
    }


    fun insert(view: View) {
        myList.add(0, Notes("XXX"))
        recycler_view.adapter?.notifyDataSetChanged()

    }
}