package com.marianunez.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marianunez.todoapp.R
import com.marianunez.todoapp.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        // recyclerView.adapter = ToDoDetailAdapter()
    }

    private fun showCreateDialog() {
        activity?.let {
            val editText = EditText(it)
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.alert_title))
                .setView(editText)
                .setPositiveButton(getString(R.string.alert_positive_button)) { dialog, _ ->
                    val task = editText.text.toString()
                    //    list.taskList.add(task)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.alert_negative_button)) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }
}