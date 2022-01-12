package com.marianunez.todoapp

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marianunez.todoapp.adapter.ToDoAdapter
import com.marianunez.todoapp.data.ListDataManager
import com.marianunez.todoapp.data.TaskList
import com.marianunez.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ToDoAdapter.ToDoListClickListener {

    private lateinit var binding: ActivityMainBinding

    // vamos a acceder a la recyclerview desde la MainActivity, por eso tenemos que añadirla
    // lateinit indica que la recyclerView se creará en un futuro no se sabe cuando
    private lateinit var recyclerView: RecyclerView

    // create a property that stores an instance of the ListDataManager class
    // passing the context to the constructor
    private val listDataManager: ListDataManager = ListDataManager(this)

    companion object {
        const val INTENT_KEY = "list"

        // this is the second param for startActivityForResult
        // it can be any number that make sense
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        binding.fab.setOnClickListener {
            showCreateDialog()
        }

    }

    private fun initUI() {
        val toDoList = listDataManager.readList()

        recyclerView = binding.toDoList
        recyclerView.layoutManager = LinearLayoutManager(this)

        // al crear una interface en el adapter también necesitamos pasarle la activity aquí
        // porque hemos añadido un clickListener al constructor
        recyclerView.adapter = ToDoAdapter(toDoList, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                val list = data.getParcelableExtra<TaskList>(INTENT_KEY)!!
                listDataManager.saveList(list)
                updateList()
            }
        }
    }

    private fun updateList() {
        // this is a way to refresh the recyclerview
        val toDoList = listDataManager.readList()
        recyclerView.adapter = ToDoAdapter(toDoList, this)
    }

    private fun showCreateDialog() {
        val editText = EditText(this)
        // this is not necessary but is to change the keyboard
        editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.alert_title))
            .setView(editText)
            // in the buttons we pass a listener and this listener takes two parameters: a dialog that we are using
            // and an Int, letting we know which button was tapped
            // but we don't need to know what button was tapped so we can put underscore
            .setPositiveButton(getString(R.string.alert_positive_button)) { dialog, _ ->
                //add new item
                // first thing is to get our adapter from the recyclerView lateinit var above
                // but this is a normal recyclerview so we need to do a cast to access our on ToDoAdapter
                val adapter = recyclerView.adapter as ToDoAdapter
                //create empty task list passing the edittext as the title
                val list = TaskList(editText.text.toString())
                // next step: save it
                listDataManager.saveList(list)
                //update recyclerview with the list (addNewItem is a method we create)
                adapter.addNewItem(list)

                showTaskDetail(list)
            }
            .setNegativeButton(getString(R.string.alert_negative_button)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    // create intent
    private fun showTaskDetail(list: TaskList) {
        val taskListDetail = Intent(this, DetailActivity::class.java)
        // putExtra daba un error porque le estamos pasando una lista y eso no está soportado
        // para solucionarlo implementamos el parsable/parcel type que es una interface
        // que podemos implementar en los objects
        taskListDetail.putExtra(INTENT_KEY, list)
        // we change startActivity for startActivityForResult
        // startActivity(taskListDetail)
        startActivityForResult(taskListDetail, LIST_DETAIL_REQUEST_CODE)
    }

    /** al añadir que la MainActivity extienda también de ToDoAdapter.ToDoListClickListener
     * necesitamos implementar esta función
     */
    override fun listItemClicked(list: TaskList) {
        showTaskDetail(list)
    }

}



