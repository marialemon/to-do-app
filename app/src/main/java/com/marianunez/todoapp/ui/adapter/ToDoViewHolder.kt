package com.marianunez.todoapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.databinding.ListItemBinding

class ToDoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemBinding.bind(view)

    val itemTitle = binding.itemTitle
    val deleteButton = binding.remove
}