package com.example.scheduleapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.model.ScheduleModel

class ChatViewModel(x: Int, y: Int): ViewModel() {
    val selected =  MutableLiveData(ScheduleModel.scheduleData[y][x])
}