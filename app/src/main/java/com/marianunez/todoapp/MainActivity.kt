package com.marianunez.todoapp

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marianunez.todoapp.data.TaskList
import com.marianunez.todoapp.databinding.ActivityMainBinding
import com.marianunez.todoapp.fragments.ToDoListFragment

class MainActivity : AppCompatActivity(), ToDoListFragment.OnFragmentInteractionListener{

    private lateinit var binding: ActivityMainBinding

    /** el listDataManager va a ser manejado por el fragment
     * así que para que funcione necesitamos crear una nueva instancia del fragment aquí
     */
    private val toDoListFragment = ToDoListFragment.newInstance()

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
        binding.fab.setOnClickListener {
            showCreateDialog()
        }

        // llamamos al Fragment Manager
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, toDoListFragment)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                val list = data.getParcelableExtra<TaskList>(INTENT_KEY)!!
                // save a list that is returned from the DetailActivity
                toDoListFragment.saveList(list)
            }
        }
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
                //create empty task list passing the edittext as the title
                val list = TaskList(editText.text.toString())
                toDoListFragment.addItem(list)
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

    // este method lo cambiamos al implementar el fragment
    override fun onToDoItemClicked(list: TaskList) {
        showTaskDetail(list)
    }

}



