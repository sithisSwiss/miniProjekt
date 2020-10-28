package com.example.miniprojekt

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import data.HistoryEntry


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindItems(historyEntry : HistoryEntry) {
        val entryView = itemView.findViewById(R.id.entry) as TextView
        val dateView = itemView.findViewById(R.id.timeStamp) as TextView
        entryView.text = historyEntry.entry
        dateView.text = historyEntry.timeStamp
    }
}

class HistoryAdapter(private val historyList: ArrayList<HistoryEntry>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context : Context = parent.context
        val inflater : LayoutInflater = LayoutInflater.from(context)


        val view : View = inflater.inflate(
            R.layout.list_layout,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(historyList[position])
    }


}