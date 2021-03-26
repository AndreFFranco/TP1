package com.example.android.tp1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.R
import com.example.android.tp1.entities.Note

class NoteAdapter internal constructor(context: Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteItemView: TextView = itemView.findViewById(R.id.note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): NoteViewHolder {

        val itemView = inflater.inflate(R.layout.recyclerline, parent, false);
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]

        holder.noteItemView.text =  current.title


    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size

}