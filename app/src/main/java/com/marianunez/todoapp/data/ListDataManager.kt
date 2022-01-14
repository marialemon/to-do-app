package com.marianunez.todoapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager

class ListDataManager(application: Application) : AndroidViewModel(application){

    private val context by lazy { application.applicationContext }

    fun saveList(list: TaskList) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPref.putStringSet(list.title, list.taskList.toHashSet())
        sharedPref.apply()
    }

    fun readList(): ArrayList<TaskList> {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val contents = sharedPref.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in contents) {
            val taskItems = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, taskItems)
            //finalmente vamos al ArrayList añadimos
            taskLists.add(list)
        }
        return taskLists
    }

}