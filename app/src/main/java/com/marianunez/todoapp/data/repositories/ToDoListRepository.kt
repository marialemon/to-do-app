package com.marianunez.todoapp.data.repositories

import com.marianunez.todoapp.data.db.ToDoListDatabase
import com.marianunez.todoapp.data.db.entities.ToDoItem

class ToDoListRepository(private val db: ToDoListDatabase) {
    suspend fun upsert(item: ToDoItem) = db.getToDoDao().upsert(item)

    suspend fun delete(item: ToDoItem) = db.getToDoDao().delete(item)

    fun getAllToDoItems() = db.getToDoDao().getAllToDoItems()
}
