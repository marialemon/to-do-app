package com.marianunez.todoapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marianunez.todoapp.adapter.ToDoAdapter
import com.marianunez.todoapp.data.ListDataManager
import com.marianunez.todoapp.data.TaskList
import com.marianunez.todoapp.databinding.FragmentTodoListBinding

class ToDoListFragment : Fragment(), ToDoAdapter.ToDoListClickListener {

    //el binding en un fragment es un poco distinto
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private var listener: OnFragmentInteractionListener? = null
    // vamos a acceder a la recyclerview desde la MainActivity, por eso tenemos que añadirla
    // lateinit indica que la recyclerView se creará en un futuro no se sabe cuando
    private lateinit var recyclerView: RecyclerView
    // create a property that stores an instance of the ListDataManager class
    // passing the context to the constructor
    private lateinit var listDataManager: ListDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /** onCreateView method is when the fragment acquires a layout
     * it must have in order to be presented within the activity
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    /** onAttach: The fragment attaches to its host activity
     * this method is run when the fragment is first associated with an activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context) //le pasamos context en vez de this porque un fragment no extiende de un context
        }
    }

    // onDetach is called when a fragment is no longer attached to an activity (obviousssly)
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onToDoItemClicked(list: TaskList)
    }

    /** This companion object is what is used to create an instance of out fragment
     *
     */
    companion object {
        fun newInstance(): ToDoListFragment = ToDoListFragment()
    }

    private fun initUI() {
        val toDoList = listDataManager.readList()

        recyclerView = binding.toDoList
        recyclerView.layoutManager = LinearLayoutManager(activity) //el context es el de la activity
        // al crear una interface en el adapter también necesitamos pasarle la activity aquí
        // porque hemos añadido un clickListener al constructor
        recyclerView.adapter = ToDoAdapter(toDoList, this)
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onToDoItemClicked(list)
    }

    fun addItem(list: TaskList) {
        listDataManager.saveList(list)
        //add new item
        // first thing is to get our adapter from the recyclerView lateinit var above
        // but this is a normal recyclerview so we need to do a cast to access our on ToDoAdapter
        val toDoAdapter = recyclerView.adapter as ToDoAdapter
        // then add new item
        toDoAdapter.addNewItem(list)
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateList()
    }

    private fun updateList() {
        // this is a way to refresh the recyclerview
        val toDoList = listDataManager.readList()
        recyclerView.adapter = ToDoAdapter(toDoList, this)
    }

}
