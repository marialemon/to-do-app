package com.marianunez.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.R

class ToDoAdapter: RecyclerView.Adapter<ToDoViewHolder>() {

    private val toDoList = mutableListOf("Android Development", "House Work", "Errands", "Shopping")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ToDoViewHolder((adapterLayout))
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.itemNumber.text = (position + 1).toString()
        holder.itemTitle.text = toDoList[position]
    }

    override fun getItemCount(): Int = toDoList.size

}