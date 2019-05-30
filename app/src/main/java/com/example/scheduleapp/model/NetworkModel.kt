package com.example.scheduleapp.model

import android.util.Log
import android.webkit.JavascriptInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object NetworkModel {
    var classData: List<ClassData> = listOf()
        private set

    var classArrayData = Array(12) { Array(7) { ClassData() } }
        private set

    var Row: Int = 0
        private set
    var Col: Int = 0
        private set

    @JavascriptInterface
    fun setClassTableSize(row: Int, col: Int) {
        Row = row
        Col = col
    }

    @JavascriptInterface
    fun setClassDatafromPosition(row: Int, col: Int, name: String, room: String) {
        if (row !in 0 until Row || col !in 0 until Col) {
            throw ArrayIndexOutOfBoundsException()
        }
        classArrayData[row][col] = ClassData(name = name, room = room)
    }
}