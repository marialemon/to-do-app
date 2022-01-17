package com.marianunez.todoapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.marianunez.todoapp.data.db.entities.ToDoItem

//this interface tells Room how we want to access our database
@Dao
interface ToDoDao {
    //update and insert
    @Insert(onConflict = OnConflictStrategy.REPLACE) // this means if the id is on the database we want to replace it instead
    suspend fun insert(item: ToDoItem)

    @Delete
    suspend fun delete(item: ToDoItem)

    @Query("SELECT * FROM to_do_items")
    fun getAllToDoItems(): LiveData<List<ToDoItem>>
}

/**
 * SQL doesn't allow to write to the Data Base in the Main thread
 * so we need to use Coroutines
 */