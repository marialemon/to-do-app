package com.marianunez.todoapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marianunez.todoapp.R
import com.marianunez.todoapp.data.db.ToDoListDatabase
import com.marianunez.todoapp.data.db.entities.ToDoItem
import com.marianunez.todoapp.data.repositories.ToDoListRepository
import com.marianunez.todoapp.databinding.FragmentTodoListBinding
import com.marianunez.todoapp.ui.ToDoListViewModel
import com.marianunez.todoapp.ui.ToDoListViewModelFactory
import com.marianunez.todoapp.ui.adapter.ToDoAdapter

class ToDoListFragment : Fragment(), ToDoAdapter.ToDoListClickListener {

    //el binding en un fragment es un poco distinto
    // este vídeo lo explica bien: https://www.youtube.com/watch?v=yE2Y2q4iWpU&ab_channel=Programaci%C3%B3nAndroidbyAristiDevs
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    // vamos a acceder a la recyclerview desde la MainActivity, por eso tenemos que añadirla
    // lateinit indica que la recyclerView se creará en un futuro no se sabe cuando
    private lateinit var recyclerView: RecyclerView

    // create a property that stores an instance of the ListDataManager class
    // passing the context to the constructor
    private lateinit var viewModel: ToDoListViewModel

    private lateinit var database: ToDoListDatabase

    /** onCreateView method is when the fragment acquires a layout
     * it must have in order to be presented within the activity
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // this is a bad practice because if we delete this fragment we lose the instances
        // create object viewmodel
        activity?.let {
            database = ToDoListDatabase(it)
        }

        val repository = ToDoListRepository(database)
        val factory = ToDoListViewModelFactory(repository)
        // to do this we need to create a ViewModelFactory and here pass it in the ViewModelProvider
        viewModel = ViewModelProvider(this, factory)[ToDoListViewModel::class.java]

        initUI()
    }

    private fun initUI() {
        val adapter = ToDoAdapter(listOf(), this, viewModel)
        recyclerView = binding.toDoList
        recyclerView.layoutManager = LinearLayoutManager(activity) //el context es el de la activity
        // al crear una interface en el adapter también necesitamos pasarle la activity aquí
        // porque hemos añadido un clickListener al constructor
        recyclerView.adapter = adapter
        // this line gets all items wich returns a livedata and the observe method means that whenever our database changes
        // the observe method will be called and the code inside it will be executed
        viewModel.getAllToDoItems().observe(viewLifecycleOwner, { item ->
            adapter.toDoList = item
            adapter.notifyDataSetChanged()
        })

        binding.fab.setOnClickListener {
            showCreateDialog()
        }
    }

    /*
    override fun listItemClicked(list: ToDoItem) {
        showTaskDetail(list)
    }
    */

    private fun showCreateDialog() {
        activity?.let {
            val editText = EditText(it) // "it" means FragmentActivity context if the activity exist
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.alert_title))
                .setView(editText)
                // in the buttons we pass a listener and this listener takes two parameters: a dialog that we are using
                // and an Int, letting we know which button was tapped
                // but we don't need to know what button was tapped so we can put underscore
                .setPositiveButton(getString(R.string.alert_positive_button)) { dialog, _ ->
                    val item = ToDoItem(editText.text.toString())
                    //create empty task list passing the edittext as the title
                    viewModel.upsert(item)
                }
                .setNegativeButton(getString(R.string.alert_negative_button)) { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }

    /*
    private fun showTaskDetail(list: ToDoItem) {
        val action =
            ToDoListFragmentDirections.actionToDoListFragmentToTaskDetailFragment(list.name)
        view?.findNavController()?.navigate(action)

    }
    */

    /** This companion object is what is used to create an instance of out fragment
     */
    companion object {
        fun newInstance(): ToDoListFragment = ToDoListFragment()
    }

}
