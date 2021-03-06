package com.example.scheduleapp.view

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.scheduleapp.databinding.*
import com.example.scheduleapp.model.ClassData
import com.example.scheduleapp.model.HeaderData
import com.example.scheduleapp.model.ScheduleModel

class ScheduleTableFragment : Fragment() {

    private lateinit var binding: FragmentScheduleTableBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentScheduleTableBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpTableLayout()
    }

    private fun setUpTableLayout() {
        for (y in 0 until ScheduleModel.Row + 1) {
            binding.tablelayout.addView(TableRow(context))
        }

        for (y in 0 until ScheduleModel.Row + 1) {
            val child = binding.tablelayout.children.toList()[y] as TableRow
            when (y) {
                0 -> {
                    for (x in 0 until ScheduleModel.Col + 1) {
                        TextviewHorizontalHeaderBinding.inflate(layoutInflater, child, true).run {
                            if (x != 0) data = ScheduleModel.scheduleData[y][x] as HeaderData
                        }
                    }
                }
                else -> {
                    TextviewVerticalHeaderBinding.inflate(layoutInflater, child, true).run {
                        data = ScheduleModel.scheduleData[y][0] as HeaderData
                    }
                    val widthDP = resources.displayMetrics.widthPixels / resources.displayMetrics.density

                    for (x in 1..ScheduleModel.Col) {
                        val classData = ScheduleModel.scheduleData[y][x] as ClassData

                        TextviewClassBinding.inflate(layoutInflater, child, true).run {
                            root.layoutParams.width = applyDimension(COMPLEX_UNIT_DIP, (widthDP - 40) / ScheduleModel.Row, resources.displayMetrics).toInt()
                            data = classData
                            if (classData.text != "") linearLayout.setBackgroundColor(classData.color)
                            else linearLayout.setBackgroundColor(Color.WHITE)

                            editButton.setOnClickListener {
                                val action = ScheduleTableFragmentDirections.actionNavigationScheduleTableToNavigationEditview(intArrayOf(x, y))
                                it.findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
        }
    }
}