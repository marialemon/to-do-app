package com.marianunez.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.R
import com.marianunez.todoapp.TaskList

class ToDoAdapter(private val toDoList: ArrayList<TaskList>) : RecyclerView.Adapter<ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ToDoViewHolder((adapterLayout))
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.itemNumber.text = (position + 1).toString()
        holder.itemTitle.text = toDoList[position].title
    }

    override fun getItemCount(): Int = toDoList.size

    fun addNewItem(list: TaskList) {
        toDoList.add(list)
        notifyItemInserted(toDoList.size - 1)
    }

}