package com.example.android.tp1.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tp1.R
import com.example.android.tp1.api.Report
import com.example.android.tp1.reports.DeleteReport
import com.example.android.tp1.reports.EditReport

const val REPT="TITLE"
const val REPD="DESCRIPTION"
const val REPID= "ID"
const val DELID= "ID"

class ReportAdapter(val report: List<Report>): RecyclerView.Adapter<ReportViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reportrecyclerline, parent, false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return report.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        return holder.bind(report[position])
    }

}

class ReportViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    private val title: TextView = itemView.findViewById(R.id.ocorrtit)
    private val description: TextView = itemView.findViewById(R.id.ocorrdesc)
    val editR: ImageButton = itemView.findViewById(R.id.editOco)
    val delR: ImageButton = itemView.findViewById(R.id.deleteOco)


    fun bind(report: Report) {
        title.text = report.title
        description.text = report.description
        editR.setOnClickListener {
            val context =  title.context
            val tit = title.text.toString()
            val desc = description.text.toString()
            val id = report.id

            val intent = Intent(context, EditReport::class.java).apply {
                putExtra(REPT, tit)
                putExtra(REPD, desc)
                putExtra(REPID, id)
            }
            context.startActivity(intent)
        }
        delR.setOnClickListener {
            val context =  title.context
            val id = report.id
            val intent = Intent(context, DeleteReport::class.java).apply {
                putExtra(DELID, id)
            }
            context.startActivity(intent)
        }
        title.text = report.title
        description.text = report.description

    }

}