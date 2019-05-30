package com.example.scheduleapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.model.ClassData
import com.example.scheduleapp.model.ClassTableData
import com.example.scheduleapp.model.HeaderData
import com.example.scheduleapp.model.NetworkModel

class ClassDataViewModel : ViewModel() {
    var classData = MutableLiveData<List<ClassTableData>>()
    val row = 6
    val col = 6

    fun refreshClassData() {
        classData.value = convert(NetworkModel.classArrayData)
    }

    private fun convert(data: Array<Array<ClassData>>): List<ClassTableData> {
        var retData = mutableListOf<ClassTableData>()

        retData.run {
            add(HeaderData(""))
            add(HeaderData("月"))
            add(HeaderData("火"))
            add(HeaderData("水"))
            add(HeaderData("木"))
            add(HeaderData("金"))
            add(HeaderData("土"))
        }
        for (i in 0 until row) {
            for (j in 0..col) {
                if (j == 0) {
                    retData.add(HeaderData((i + 1).toString()))
                } else {
                    retData.add(data[i][j - 1])
                }
            }
        }

        return retData
    }
}