package com.example.scheduleapp.view
import android.os.Bundle
import android.util.Log
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.applyDimension
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.scheduleapp.databinding.*
import com.example.scheduleapp.model.ClassData
import com.example.scheduleapp.model.HeaderData
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
        setUpTableLayout()
        Log.d("Debug", "SetUP")
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
                        val horizontalBinding = TextviewHorizontalHeaderBinding.inflate(layoutInflater, child, true)
                        if (x != 0) horizontalBinding.data = ScheduleModel.scheduleData[y][x] as HeaderData
                    }
                }
                else -> {
                    val verticalHeaderBinding = TextviewVerticalHeaderBinding.inflate(layoutInflater, child, true)
                    verticalHeaderBinding.data = ScheduleModel.scheduleData[y][0] as HeaderData
                    val widthDP = resources.displayMetrics.widthPixels / resources.displayMetrics.density

                    for (x in 1..ScheduleModel.Col) {
                        val classBinding = TextviewClassBinding.inflate(layoutInflater, child, true)
                        classBinding.root.layoutParams.width = applyDimension(COMPLEX_UNIT_DIP, (widthDP - 40) / ScheduleModel.Row, resources.displayMetrics).toInt()
                        classBinding.data = ScheduleModel.scheduleData[y][x] as ClassData
                    }
                }
            }
        }
    }
}