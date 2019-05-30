package com.example.scheduleapp.model

enum class Semester {
    AllSeason, Spring, Fall
}

enum class WeekDay {
    Mon, Tue, Wed, Thu, Fri, Sat, Sun
}

data class ClassData(
    val id: Int = 0,
    val year: Int = 0,
    val thPeriod: Int = 0,
    val semester: Semester = Semester.AllSeason,
    val weekDay: WeekDay = WeekDay.Mon,
    override val name: String = "test",
    val teacher: String = "",
    val room: String = ""
) : ClassTableData

data class HeaderData(override val name: String) : ClassTableData

interface ClassTableData { val name: String }