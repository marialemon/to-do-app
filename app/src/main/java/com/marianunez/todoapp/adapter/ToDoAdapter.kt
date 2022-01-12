package com.marianunez.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.R
import com.marianunez.todoapp.data.TaskList

class ToDoAdapter(
    private val toDoList: ArrayList<TaskList>,
    private val clickListener: ToDoListClickListener
) : RecyclerView.Adapter<ToDoViewHolder>() {

    /** creamos una interface para que el usuario pueda pulsar cada item y ver la vista detalle
     * una vez tenemos la interface definida, tenemos que crear un listener y podemos hacerlo
     * en el constructor de nuestra clase, como parámetro
     * después tenemos que editar el onBindViewHolder para añadir el listener a la view
     */
    interface ToDoListClickListener {
        fun listItemClicked(list: TaskList) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ToDoViewHolder((adapterLayout))
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.itemNumber.text = (position + 1).toString()
        holder.itemTitle.text = toDoList[position].title
        holder.itemView.setOnClickListener {
            //llamamos al clickListener que pasamos al constructor y a la función de la interface
            clickListener.listItemClicked(toDoList[position])
        }
    }

    override fun getItemCount(): Int = toDoList.size

    fun addNewItem(list: TaskList) {
        toDoList.add(list)
        notifyItemInserted(toDoList.size - 1)
    }

}