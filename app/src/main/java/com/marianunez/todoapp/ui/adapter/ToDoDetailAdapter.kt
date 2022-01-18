package com.marianunez.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.R
import com.marianunez.todoapp.data.TaskList

class ToDoDetailAdapter(private val toDoDetailList: TaskList) :
    RecyclerView.Adapter<ToDoDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoDetailViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_list_item, parent, false)
        return ToDoDetailViewHolder((adapterLayout))
    }

    override fun onBindViewHolder(holder: ToDoDetailViewHolder, position: Int) {
        holder.itemCheckbox.text = toDoDetailList.taskList[position]
    }

    override fun getItemCount(): Int = toDoDetailList.taskList.size

}

