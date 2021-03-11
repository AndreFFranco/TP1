package com.example.android.tp1.adapter

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.R
import com.example.android.tp1.dataclasses.Note
import java.text.FieldPosition

class LineAdapter(val list: ArrayList<Note>):RecyclerView.Adapter<LineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): LineViewHolder {

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

        holder.title.text = currentNote.title
    }
}

class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.title
}