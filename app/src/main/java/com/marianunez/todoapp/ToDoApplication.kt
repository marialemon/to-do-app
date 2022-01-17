package com.marianunez.todoapp

import android.app.Application
import com.marianunez.todoapp.data.db.ToDoListDatabase
import com.marianunez.todoapp.data.repositories.ToDoListRepository
import com.marianunez.todoapp.ui.ToDoListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ToDoApplication() : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ToDoApplication))
        bind() from singleton { ToDoListDatabase(instance()) }
        bind() from singleton { ToDoListRepository(instance()) }
        bind() from provider { ToDoListViewModelFactory(instance()) }
    }

}