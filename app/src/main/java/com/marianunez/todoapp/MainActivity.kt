package com.marianunez.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marianunez.todoapp.fragments.ToDoListFragment

class MainActivity : AppCompatActivity(){
    /** el listDataManager va a ser manejado por el fragment
     * así que para que funcione necesitamos crear una nueva instancia del fragment aquí
     */
    private val toDoListFragment = ToDoListFragment.newInstance()

    companion object {
        const val INTENT_KEY = "list"
        // this is the second param for startActivityForResult
        const val LIST_DETAIL_REQUEST_CODE = 123 // it can be any number that make sense
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // llamamos al Fragment Manager
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, toDoListFragment)
            .commit()
    }

}



