package com.marianunez.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marianunez.todoapp.data.TaskList
import com.marianunez.todoapp.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var list: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getParcelable(ARG_LIST)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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