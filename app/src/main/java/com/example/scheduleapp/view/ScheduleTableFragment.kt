package com.example.scheduleapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.databinding.FragmentScheduleTableBinding
import com.example.scheduleapp.model.ScheduleModel
import com.example.scheduleapp.viewmodel.ScheduleDataViewModel

class ScheduleTableFragment : Fragment() {

    private lateinit var scheduleDataViewModel: ScheduleDataViewModel
    private lateinit var binding: FragmentScheduleTableBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        scheduleDataViewModel = ViewModelProvider.NewInstanceFactory().create(ScheduleDataViewModel::class.java)
        binding = FragmentScheduleTableBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerAdapter()
    }

    private fun setUpRecyclerAdapter() {
        val viewManager: RecyclerView.LayoutManager
        val viewAdapter: RecyclerView.Adapter<*>

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        viewManager = GridLayoutManager(context, ScheduleModel.Col + 1, LinearLayoutManager.VERTICAL, false)
        scheduleDataViewModel.refreshClassData()
        Log.d("Debug", "refreshClassData")
        viewAdapter = ScheduleTableAdapter(context, scheduleDataViewModel)
        binding.recyclerView.run {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(itemDecoration)
        }
    }
}