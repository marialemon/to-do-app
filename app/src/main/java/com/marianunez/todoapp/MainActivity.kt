package com.marianunez.todoapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.adapter.ToDoAdapter
import com.marianunez.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // vamos a acceder a la recyclerview desde la MainActivity, por eso tenemos que añadirla
    // lateinit indica que la recyclerView se creará en un futuro no se sabe cuando
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        initUI()
    }

    private fun initUI(){
        recyclerView = binding.toDoList
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ToDoAdapter()
    }


}