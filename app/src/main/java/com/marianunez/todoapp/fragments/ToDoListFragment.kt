package com.marianunez.todoapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marianunez.todoapp.DetailActivity
import com.marianunez.todoapp.MainActivity
import com.marianunez.todoapp.R
import com.marianunez.todoapp.adapter.ToDoAdapter
import com.marianunez.todoapp.data.ListDataManager
import com.marianunez.todoapp.data.TaskList
import com.marianunez.todoapp.databinding.FragmentTodoListBinding

class ToDoListFragment : Fragment(), ToDoAdapter.ToDoListClickListener {

    //el binding en un fragment es un poco distinto
    // este vídeo lo explica bien: https://www.youtube.com/watch?v=yE2Y2q4iWpU&ab_channel=Programaci%C3%B3nAndroidbyAristiDevs
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    // vamos a acceder a la recyclerview desde la MainActivity, por eso tenemos que añadirla
    // lateinit indica que la recyclerView se creará en un futuro no se sabe cuando
    private lateinit var recyclerView: RecyclerView

    // create a property that stores an instance of the ListDataManager class
    // passing the context to the constructor
    private lateinit var listDataManager: ListDataManager

    /** onCreateView method is when the fragment acquires a layout
     * it must have in order to be presented within the activity
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            listDataManager = ListDataManager(it)
        }

        binding.fab.setOnClickListener {
            showCreateDialog()
        }

        initUI()

    }

    /** This companion object is what is used to create an instance of out fragment
     */
    companion object {
        fun newInstance(): ToDoListFragment = ToDoListFragment()
    }

    private fun initUI() {
        val toDoList = listDataManager.readList()

        recyclerView = binding.toDoList
        recyclerView.layoutManager = LinearLayoutManager(activity) //el context es el de la activity
        // al crear una interface en el adapter también necesitamos pasarle la activity aquí
        // porque hemos añadido un clickListener al constructor
        recyclerView.adapter = ToDoAdapter(toDoList, this)
    }

    fun addItem(list: TaskList) {
        listDataManager.saveList(list)
        //add new item
        // first thing is to get our adapter from the recyclerView lateinit var above
        // but this is a normal recyclerview so we need to do a cast to access our on ToDoAdapter
        val toDoAdapter = recyclerView.adapter as ToDoAdapter
        // then add new item
        toDoAdapter.addNewItem(list)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateList()
    }

    private fun updateList() {
        // this is a way to refresh the recyclerview
        val toDoList = listDataManager.readList()
        recyclerView.adapter = ToDoAdapter(toDoList, this)
    }

    private fun showCreateDialog() {
        activity?.let {
            val editText = EditText(it) // "it" means FragmentActivity context if the activity exist
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.alert_title))
                .setView(editText)
                // in the buttons we pass a listener and this listener takes two parameters: a dialog that we are using
                // and an Int, letting we know which button was tapped
                // but we don't need to know what button was tapped so we can put underscore
                .setPositiveButton(getString(R.string.alert_positive_button)) { dialog, _ ->
                    //create empty task list passing the edittext as the title
                    val list = TaskList(editText.text.toString())
                    addItem(list)
                    showTaskDetail(list)
                }
                .setNegativeButton(getString(R.string.alert_negative_button)) { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }

    // create intent
    private fun showTaskDetail(list: TaskList) {
        val taskListDetail = Intent(context, DetailActivity::class.java)
        // putExtra daba un error porque le estamos pasando una lista y eso no está soportado
        // para solucionarlo implementamos el parsable/parcel type que es una interface
        // que podemos implementar en los objects
        taskListDetail.putExtra(MainActivity.INTENT_KEY, list)
        // we change startActivity for startActivityForResult
        // startActivity(taskListDetail)
        startActivityForResult(taskListDetail, MainActivity.LIST_DETAIL_REQUEST_CODE)
    }

}
