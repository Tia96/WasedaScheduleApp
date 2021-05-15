package com.example.scheduleapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.model.ClassData
import com.example.scheduleapp.model.ScheduleModel

class EditViewModel(x: Int, y: Int) : ViewModel() {
    var selected = MutableLiveData(ScheduleModel.scheduleData[y][x] as ClassData)
    val title = when (x) {
        1 -> "月曜"
        2 -> "火曜"
        3 -> "水曜"
        4 -> "木曜"
        5 -> "金曜"
        6 -> "土曜"
        7 -> "日曜"
        else -> ""
    } + y.toString() + "限"
}