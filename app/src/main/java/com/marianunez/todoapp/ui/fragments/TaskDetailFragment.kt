package com.marianunez.todoapp.ui.fragments

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
import com.marianunez.todoapp.data.ListDataManager
import com.marianunez.todoapp.ui.adapter.ToDoDetailAdapter
import com.marianunez.todoapp.data.TaskList
import com.marianunez.todoapp.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var list: TaskList  // property that contains our TaskList
    private lateinit var recyclerView: RecyclerView
    lateinit var listDataManager: ListDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDataManager = ViewModelProvider(this).get(ListDataManager::class.java)
        arguments?.let {
            val args = TaskDetailFragmentArgs.fromBundle(it)
            list = listDataManager.readList().filter { list -> list.title == args.listString }[0]
        }

        activity?.let {
            initUI()

            binding.fab.setOnClickListener {
                showCreateDialog()
            }
        }
    }

    private fun initUI() {
        recyclerView = binding.toDoList
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ToDoDetailAdapter(list)
    }


    private fun showCreateDialog() {
        activity?.let {
            val editText = EditText(it)
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.alert_title))
                .setView(editText)
                .setPositiveButton(getString(R.string.alert_positive_button)) { dialog, _ ->
                    val task = editText.text.toString()
                    list.taskList.add(task)
                    listDataManager.saveList(list)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.alert_negative_button)) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }


    companion object {
        // now we are going to implement the way to access the list using a bundle
        private const val ARG_LIST = "list"

        fun newInstance(list: TaskList) : TaskDetailFragment {
            val bundle = Bundle()
            // put the list into de Bundle
            bundle.putParcelable(ARG_LIST, list)
            // create the fragment
            val fragment = TaskDetailFragment()
            // put the bundle inside of the fragment and return it
            fragment.arguments = bundle
            return fragment
        }
    }

}