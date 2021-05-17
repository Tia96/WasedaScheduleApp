package com.example.scheduleapp.model

import android.webkit.JavascriptInterface
import org.json.JSONObject

enum class Semester { AllSeason, Spring, Fall }

enum class WeekDay(val day: Int) { None(0), Mon(1), Tue(2), Wed(3), Thu(4), Fri(5), Sat(6), Sun(7) }

enum class ScheduleType {Class,  VerticalHeader, HorizontalHeader}

data class ClassData(
    override val type: ScheduleType = ScheduleType.Class,
    override var text: String = "",
    val year: Int = 0,
    val thPeriod: Int = 0,
    val semester: Semester = Semester.AllSeason,
    val weekDay: WeekDay = WeekDay.Mon,
    val teachers: String = "",
    val room: String = "",
    val chats: MutableList<String> = mutableListOf(),
    val bitmapCache: MutableList<String> = mutableListOf()
) : ScheduleData

data class HeaderData(override val type: ScheduleType, override var text: String) : ScheduleData

interface ScheduleData {
    val type: ScheduleType
    var text: String
}

object ScheduleModel {
    private const val MaxRow = 12
    private const val MaxCol = 8
    var Row: Int = 6
    var Col: Int = 6

    //classDataやheaderなどの情報も含めた配列
    var scheduleData: Array<Array<ScheduleData>> = Array(MaxRow) { Array(MaxCol) { ClassData() } }

    init {
        for (y in 1 until MaxRow) {
            scheduleData[y][0] = HeaderData(type = ScheduleType.VerticalHeader, text = y.toString())
        }
        for (x in 0 until MaxCol) {
            scheduleData[0][x] = HeaderData(type = ScheduleType.HorizontalHeader, text = WeekDay.values()[x].toString())
        }
    }

    @JavascriptInterface
    fun setDataFromJson(data: String) {
        val jsonObj = JSONObject(data)
        val semester = when (jsonObj.getString("Semester")) {
            "春学期", "spring\nsemester" -> Semester.Spring
            "秋学期", "fall\nsemester" -> Semester.Fall
            else -> Semester.AllSeason
        }
        val weekDay = when (jsonObj.getString("WeekDay")) {
            "月", "Mon." -> WeekDay.Mon
            "火", "Tues." -> WeekDay.Tue
            "水", "Wed." -> WeekDay.Wed
            "木", "Thu." -> WeekDay.Thu
            "金", "Fri." -> WeekDay.Fri
            "土", "Sat." -> WeekDay.Sat
            "日", "Sun." -> WeekDay.Sun
            else -> WeekDay.None
        }
        val thPeriod = when (jsonObj.getString("thPeriod")) {
            "1", "2", "3", "4", "5", "6", "7", "8" -> jsonObj.getInt("thPeriod")
            else -> MaxRow - 1
        }
        val name = jsonObj.getString("Name")
        val teachers = jsonObj.getString("Teachers")
        val room = jsonObj.getString("Room")
        scheduleData[thPeriod][weekDay.day] = ClassData(text = name, thPeriod = thPeriod, semester = semester, teachers = teachers, room = room)
    }
}