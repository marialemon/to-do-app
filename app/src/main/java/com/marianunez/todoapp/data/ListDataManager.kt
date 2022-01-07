package com.marianunez.todoapp.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.marianunez.todoapp.TaskList

class ListDataManager(private val context: Context) {

    // ponemos mode 0 porque 0 significa un modo que después vamos a usar
    // val sharedPref: SharedPreferences = context.getSharedPreferences(SHARED_NAME, 0)

    /*
    var task: String?
        get() = sharedPref.getString(SHARED_TASK_NAME, "")
        set(value) = sharedPref.edit().putString(SHARED_TASK_NAME, value).apply()
     */

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