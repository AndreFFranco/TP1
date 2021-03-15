package com.example.android.tp1.adapter

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.R
import com.example.android.tp1.dataclasses.Note
import com.example.android.tp1.entities.Note
import kotlinx.android.synthetic.main.recyclerline.view.*
import java.text.FieldPosition

class LineAdapter internal constructor(context: Context) : RecyclerView.Adapter<LineAdapter.LineViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): LineViewHolder {

        val itemView = inflater.inflate(R.layout.recyclerline, parent, false);
        return LineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentNote = notes[position]

        holder.title.text = currentNote.title
    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}

class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.title1
}