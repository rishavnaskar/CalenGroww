package com.example.calengroww.activities

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calengroww.R
import com.example.calengroww.adapters.CalenAdapter
import com.example.calengroww.adapters.MonthAdapter
import com.example.calengroww.adapters.YearAdapter
import com.example.calengroww.calender.CalenCalendar
import com.example.calengroww.data.CalenDate
import com.example.calengroww.databinding.ActivityMainBinding
import com.example.calengroww.viewmodel.CalenViewModel

class MainActivity : AppCompatActivity() {
    private val calenViewModel: CalenViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "CalenGroww"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#0A163E")))

        // Initialize day, month, year views
        initializeViews()

        // Initialize Observer
        calenViewModel.calenLiveData.observe(this) { date ->
            initDateView(date)
            initWeekInfoView(date)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.pick_date, menu)
        menuInflater.inflate(R.menu.today_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.resetDate -> {
                resetDate()
                return true
            }
            R.id.pickDate -> {
                pickDate()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeViews() {
        initYearView()
        initMonthView()
        initCalendarView()
    }

    private fun initYearView() {
        val years = (1800..2200).toList()
        val initialYear = calenViewModel.calenLiveData.value!!.year
        val initialYearOffset = initialYear - years[0]
        val yearAdapter = YearAdapter(years, initialYearOffset, this) { newYear ->
            onYearChanged(newYear)
        }
        binding.yearRecyclerView.apply {
            adapter = yearAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            layoutManager?.scrollToPosition(initialYearOffset - 2)
        }
    }

    private fun initMonthView() {
        // Handling the month recycler view
        val initialMonth = calenViewModel.calenLiveData.value!!.month
        val monthAdapter = MonthAdapter(CalenCalendar.months, initialMonth, this) { newMonth ->
            onMonthChanged(newMonth)
        }

        binding.monthRecyclerView.apply {
            adapter = monthAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            layoutManager?.scrollToPosition(if (initialMonth != 0) initialMonth - 1 else initialMonth)
        }
    }

    private fun initWeekInfoView(date: CalenDate) {
        binding.weekInfoTextView.text = calenViewModel.getWeekInfoString(date)
    }

    private fun initDateView(date: CalenDate) {
        binding.selectedDateTextView.text = calenViewModel.getStringFormattedDate(date)
    }

    private fun resetDate() {
        calenViewModel.resetDateToday()
        initializeViews()
        Toast.makeText(this, "Current Date selected", Toast.LENGTH_SHORT).show()
    }

    private fun pickDate() {
        val calenDateListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                onDatePicked(year, month, day)

            }
        DatePickerDialog(
            this,
            R.style.DialogTheme,
            calenDateListener,
            calenViewModel.getYear(),
            calenViewModel.getMonth(),
            calenViewModel.getDay()
        ).show()
    }

    private fun onDatePicked(year: Int, month: Int, day: Int) {
        calenViewModel.updatePickedDate(year, month, day)
        initializeViews()
    }


    private fun onYearChanged(year: Int) {
        calenViewModel.updateYear(year)
        initCalendarView()
    }


    private fun onMonthChanged(month: Int) {
        calenViewModel.updateMonth(month)
        initCalendarView()
    }

    private fun onDayChanged(shiftedDay: Int) {
        calenViewModel.updateShiftedDay(shiftedDay)
    }

    private fun initCalendarView() {
        val days = calenViewModel.getFormattedDaysInMonth()
        val shiftedDay = calenViewModel.getShiftedDay()

        val calendarAdapter = CalenAdapter(days, shiftedDay, this) { newShiftedDay ->
            onDayChanged(newShiftedDay)
        }

        binding.calendarRecyclerView.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 7)
            setHasFixedSize(true)
        }
    }
}