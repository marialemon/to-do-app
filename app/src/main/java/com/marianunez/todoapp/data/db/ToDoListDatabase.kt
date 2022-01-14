package com.marianunez.todoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marianunez.todoapp.data.db.entities.ToDoItem

@Database(
    entities = [ToDoItem::class],
    version = 1 //everytime we update the database we need to change the version number, otherwise Room will throw an error
)
abstract class ToDoListDatabase : RoomDatabase() {
    // we need to create a fun that refers our Dao object to access the methods in it
    abstract fun getToDoDao(): ToDoDao

    companion object {
        // we have to create an instance of the ToDoListDatabase inside of the class
        // Singleton
        /** Volatile annotation make this instance visible to another threads and its to avoid potential bugs
         * we add it because we want to make sure that only one thread at a time is writing to that instance
         * because otherwise it could be two threads who want to initialize that instance
         * and we don't want two instances of the same database
         */
        @Volatile
        private var instance: ToDoListDatabase? = null

        // this means that this function will be executed whenever we create an instance of our database class
        // so whenever we write ToDoListDatabase() this function will be executed
        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            //so inside we call our instance and again if its null we create a database
            instance ?: createDatabase(context).also { instance = it }
        }
        //syncronized() makes sure that no other threads will access that instance at the same time

        // method to instantiate our actual database
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ToDoListDatabase::class.java,
                "ToDoListDB.db"
            ).build()

    }
}