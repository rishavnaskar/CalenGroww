package com.example.calengroww.calender

import com.example.calengroww.data.CalenDate
import java.text.SimpleDateFormat
import java.util.*

class CalenCalendar {
    companion object {
        private val calendar = Calendar.getInstance()

        fun getCurrentYear(): Int {
            return calendar.get(Calendar.YEAR)
        }

        fun getCurrentMonth(): Int {
            return calendar.get(Calendar.MONTH)
        }

        fun getCurrentDay(): Int {
            return calendar.get(Calendar.DAY_OF_MONTH)
        }

        fun getNumberOfDaysInMonthList(month: Int, year: Int): List<Int> {
            val gregorianCalendar = GregorianCalendar(year, month, 1)
            return (1..gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)).toList()
        }

        fun getWeekOfYear(date: CalenDate): Int {
            val gregorianCalendar = GregorianCalendar(date.year, date.month, date.day)
            return gregorianCalendar.get(Calendar.WEEK_OF_YEAR)
        }

        fun getWeekOfMonth(date: CalenDate): Int {
            val gregorianCalendar = GregorianCalendar(date.year, date.month, date.day)
            return gregorianCalendar.get(Calendar.WEEK_OF_MONTH)
        }

        fun getShiftedDay(day: Int, month: Int, year: Int): Int {
            val gregorianCalendar = GregorianCalendar(year, month, 1)
            val firstDay = gregorianCalendar.get(Calendar.DAY_OF_WEEK)
            return day + firstDay - 2
        }

        fun getNonShiftedDay(shiftedDay: Int, month: Int, year: Int): Int {
            val gregorianCalendar = GregorianCalendar(year, month, 1)
            val firstDay = gregorianCalendar.get(Calendar.DAY_OF_WEEK)
            return shiftedDay - firstDay + 2
        }

        fun getFormattedDaysInMonth(month: Int, year: Int): List<String> {
            val daysInMonthList = mutableListOf<String>()
            val gregorianCalendar = GregorianCalendar(year, month, 1)
            val daysInMonth = gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val firstDayOfWeek = gregorianCalendar.get(Calendar.DAY_OF_WEEK)

            for (i in 1..42) {
                if (i < firstDayOfWeek || i >= firstDayOfWeek + daysInMonth) {
                    daysInMonthList.add("")
                } else {
                    daysInMonthList.add("${i - firstDayOfWeek + 1}")
                }
            }

            return daysInMonthList
        }

        fun getWeekInRange(date: CalenDate): String {
            val gregorianCalendar = GregorianCalendar(date.year, date.month, date.day)
            val shift =
                gregorianCalendar.get(Calendar.DAY_OF_WEEK) - gregorianCalendar.firstDayOfWeek

            gregorianCalendar.add(Calendar.DATE, -shift)
            val startTime = gregorianCalendar.time

            gregorianCalendar.add(Calendar.DATE, 6)
            val endTime = gregorianCalendar.time

            val simpleDateFormat = SimpleDateFormat("dd/MM/yy", Locale.US)
            return "${simpleDateFormat.format(startTime)} - ${simpleDateFormat.format(endTime)}"
        }

        fun getStringFormattedDate(date: CalenDate): String {
            val simpleDateFormatIn = SimpleDateFormat("d M y", Locale.US)
            val inDate = simpleDateFormatIn.parse("${date.day} ${date.month + 1} ${date.year}")

            val simpleDateFormatOut = SimpleDateFormat("EEEE, dd MMMM, y", Locale.US)
            return simpleDateFormatOut.format(inDate!!)
        }

        val months = listOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",
        )
    }
}