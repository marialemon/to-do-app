package com.marianunez.todoapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.databinding.DetailListItemBinding

class ToDoDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = DetailListItemBinding.bind(view)

    val itemCheckbox = binding.checkbox
}