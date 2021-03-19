package com.example.android.tp1.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.R
import com.example.android.tp1.dataclasses.Notes
import kotlinx.android.synthetic.main.recyclerline.view.*


class LineAdapter(val list: ArrayList<Notes>):RecyclerView.Adapter<LineViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerline, parent, false);
        return LineViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentNote = list[position]

        holder.note.text = currentNote.notes
    }
}

class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val note = itemView.note
}

