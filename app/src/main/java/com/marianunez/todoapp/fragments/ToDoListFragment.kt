package com.marianunez.todoapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marianunez.todoapp.R
import com.marianunez.todoapp.data.TaskList

class ToDoListFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

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
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    /** onAttach: The fragment attaches to its host activity
     * this method is run when the fragment is first associated with an activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
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
}
