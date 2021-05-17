package com.example.scheduleapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.model.ClassData
import com.example.scheduleapp.model.ScheduleModel

class ChatViewModel(x: Int, y: Int): ViewModel() {
    val selected =  ScheduleModel.scheduleData[y][x] as ClassData
    val editText = MutableLiveData("")

    fun addChatText(str: String) {
        selected.chats.add(str)
    }

    fun addChatImage(str: String) {
        selected.bitmapCache.add(str)
    }
}