package com.example.scheduleapp.view.classTable

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleapp.R
import com.example.scheduleapp.databinding.ClassTableFragBinding
import com.example.scheduleapp.viewmodel.ClassDataViewModel
import com.example.scheduleapp.viewmodel.ClassTableAdapter
import kotlinx.android.synthetic.main.class_table_frag.*

class ClassTableFragment : Fragment() {

    lateinit var classDataViewModel: ClassDataViewModel
    lateinit var binding: ClassTableFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        classDataViewModel = ViewModelProviders.of(this).get(ClassDataViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.class_table_frag, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerAdapter()
    }

    private lateinit var recyclerView: RecyclerView

    fun setUpRecyclerAdapter() {
        val viewManager: RecyclerView.LayoutManager
        val viewAdapter: RecyclerView.Adapter<*>

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        viewManager = GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false)
        classDataViewModel.refreshClassData()
        viewAdapter = ClassTableAdapter(context, classDataViewModel)
        recyclerView = recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(itemDecoration)
        }
    }
}