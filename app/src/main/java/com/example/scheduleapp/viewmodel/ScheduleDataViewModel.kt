package com.example.scheduleapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.model.ScheduleData
import com.example.scheduleapp.model.ScheduleModel

class ScheduleDataViewModel : ViewModel() {
    var scheduleData = MutableLiveData<List<ScheduleData>>()

    fun refreshClassData() {
        scheduleData.value = scheduleArrayToList(ScheduleModel.scheduleData)
    }

    private fun scheduleArrayToList(data: Array<Array<ScheduleData>>): List<ScheduleData> {
        val scheduleList = mutableListOf<ScheduleData>()
        for (y in 0 until ScheduleModel.Row + 1) {
            for (x in 0 until ScheduleModel.Col + 1) {
                    scheduleList.add(data[y][x])
            }
        }
        return scheduleList
    }
}