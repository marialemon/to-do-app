package com.marianunez.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.R
import com.marianunez.todoapp.data.db.entities.ToDoItem
import com.marianunez.todoapp.ui.ToDoListViewModel

class ToDoAdapter(
    var toDoList: List<ToDoItem>,
    private val clickListener: ToDoListClickListener,
    private val viewModel: ToDoListViewModel
) : RecyclerView.Adapter<ToDoViewHolder>() {

    /** creamos una interface para que el usuario pueda pulsar cada item y ver la vista detalle
     * una vez tenemos la interface definida, tenemos que crear un listener y podemos hacerlo
     * en el constructor de nuestra clase, como parámetro
     * después tenemos que editar el onBindViewHolder para añadir el listener a la view
     */
    interface ToDoListClickListener {
        fun listItemClicked(list: ToDoItem) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ToDoViewHolder((adapterLayout))
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val currentListItem = toDoList[position]
        holder.itemTitle.text = currentListItem.name
        holder.itemView.setOnClickListener {
            //llamamos al clickListener que pasamos al constructor y a la función de la interface
            clickListener.listItemClicked(currentListItem)
        }
        holder.deleteButton.setOnClickListener {
            viewModel.delete(currentListItem)
        }
    }

    override fun getItemCount(): Int = toDoList.size
}