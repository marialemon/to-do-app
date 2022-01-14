package com.marianunez.todoapp.data.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//this is going to be the table of our data base
@Entity(tableName = "to_do_items")
data class ToDoItem(
    @NonNull @ColumnInfo(name = "task_name") var name: String,
) {
    @PrimaryKey(autoGenerate = true) //this means that Room will generate all the id's for us
    var id: Int? = null
}
