package com.marianunez.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marianunez.todoapp.data.db.entities.ToDoItem
import com.marianunez.todoapp.data.repositories.ToDoListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoListViewModel(private val repository: ToDoListRepository) : ViewModel() {
    // this is not a suspend fun because inside of it we are going to do a coroutine
    // we put Dispatchers.Main because Main is Main context thread and Room provides Main safety (?)
    fun upsert(item: ToDoItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }

    fun delete(item: ToDoItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    //this doesn't have to be in a CoroutineScope because is a readonly operation
    fun getAllToDoItems() = repository.getAllToDoItems()
}

class ToDoListViewModelFactory(
    private val repository: ToDoListRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}