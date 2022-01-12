package com.marianunez.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marianunez.todoapp.adapter.ToDoDetailAdapter
import com.marianunez.todoapp.data.TaskList
import com.marianunez.todoapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var list: TaskList  // property that contains our TaskList
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = intent.getParcelableExtra(MainActivity.INTENT_KEY) ?: TaskList as TaskList
        title = list.title

        initUI()

        binding.fab.setOnClickListener {
            showCreateDialog()
        }
    }

    private fun initUI() {
        recyclerView = binding.toDoList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ToDoDetailAdapter(list)
    }

    /** When this method is called, creamos un bundle,
     * lo ponemos en nuestra lista con la list key
     * creamos un intent
     * ponemos el bundle en el intent
     * y lo enviamos
     */
    override fun onBackPressed() {
        // a bundle is to store things
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_KEY, list)

        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)

        super.onBackPressed()
    }

    private fun showCreateDialog() {
        val editText = EditText(this)
        // this is not necessary but is to change the keyboard
        editText.inputType = InputType.TYPE_CLASS_TEXT

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.alert_title))
            .setView(editText)
            .setPositiveButton(getString(R.string.alert_positive_button)) { dialog, _ ->
                val task = editText.text.toString()
                list.taskList.add(task)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.alert_negative_button)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}