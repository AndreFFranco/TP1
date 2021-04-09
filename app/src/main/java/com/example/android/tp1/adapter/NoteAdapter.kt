package com.example.android.tp1.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.R
import com.example.android.tp1.ViewNoteActivity
import com.example.android.tp1.entities.Note
import kotlinx.android.synthetic.main.recyclerline.view.*

class NoteAdapter internal constructor(context: Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleview: TextView = itemView.findViewById(R.id.notetitle)
        val descview: TextView = itemView.findViewById(R.id.desc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerline, parent, false);
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current: Note = notes[position]

        val id = current.id
        holder.titleview.text =  current.title
        holder.descview.text =  current.description

        holder.itemView.item.setOnClickListener {

            val context = holder.titleview.context

            val title = holder.titleview.text.toString()
            val description = holder.descview.text.toString()

            val intent = Intent(context, ViewNoteActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("title", title)
            intent.putExtra("description", description)

            context.startActivity(intent)
        }
    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size



}
