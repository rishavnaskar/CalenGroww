package com.example.calengroww.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calengroww.calender.CalenCalendar
import com.example.calengroww.data.CalenDate

class CalenViewModel : ViewModel() {

    private val _calenLiveData = MutableLiveData(
        CalenDate(
            CalenCalendar.getCurrentDay(),
            CalenCalendar.getCurrentMonth(),
            CalenCalendar.getCurrentYear()
        )
    )

    val calenLiveData = _calenLiveData

    fun getYear(): Int = CalenCalendar.getCurrentYear()
    fun getMonth(): Int = CalenCalendar.getCurrentMonth()
    fun getDay(): Int = CalenCalendar.getCurrentDay()

    fun resetDateToday() {
        _calenLiveData.value = CalenDate(
            CalenCalendar.getCurrentDay(),
            CalenCalendar.getCurrentMonth(),
            CalenCalendar.getCurrentYear()
        )
    }


    fun updateMonth(month: Int) {
        _calenLiveData.value?.month = month
        _calenLiveData.postValue(_calenLiveData.value)
    }

    fun updateYear(year: Int) {
        _calenLiveData.value?.year = year
        _calenLiveData.postValue(_calenLiveData.value)
    }

    fun updatePickedDate(year: Int, month: Int, day: Int) {
        _calenLiveData.value?.year = year
        _calenLiveData.value?.month = month
        _calenLiveData.value?.day = day
        _calenLiveData.postValue(_calenLiveData.value)
    }

    fun getStringFormattedDate(date: CalenDate): String {
        return CalenCalendar.getStringFormattedDate(date)
    }

    fun updateNonShiftedDay(nonShiftedDay: Int) {
        _calenLiveData.value?.day = nonShiftedDay
        _calenLiveData.postValue(_calenLiveData.value)
    }

    fun updateShiftedDay(shiftedDay: Int) {
        val date = getNonShiftedDay(shiftedDay)
        _calenLiveData.value?.day = date
        _calenLiveData.postValue(_calenLiveData.value)
    }

    fun getFormattedDaysInMonth(): List<String> {
        return CalenCalendar.getFormattedDaysInMonth(
            _calenLiveData.value!!.month,
            _calenLiveData.value!!.year
        )
    }

    fun getWeekInfoString(date: CalenDate): String {
        val weekOfYear = CalenCalendar.getWeekOfYear(date).toString()
        val weekOfMonth = CalenCalendar.getWeekOfMonth(date).toString()
        val weekRange = CalenCalendar.getWeekInRange(date)
        return "$weekRange\n$weekOfYear weeks from the year start\n$weekOfMonth weeks from the month start"
    }

    fun getShiftedDay(): Int {
        return CalenCalendar.getShiftedDay(
            _calenLiveData.value!!.day,
            _calenLiveData.value!!.month,
            _calenLiveData.value!!.year
        )
    }

    private fun getNonShiftedDay(shiftedDay: Int): Int {
        return CalenCalendar.getNonShiftedDay(
            shiftedDay,
            _calenLiveData.value!!.month,
            _calenLiveData.value!!.year
        )
    }
}