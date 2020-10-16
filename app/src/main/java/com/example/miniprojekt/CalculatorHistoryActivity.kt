package com.example.miniprojekt

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import controller.CalculatorHistoryController
import data.HistoryEntry
import java.io.File

class CalculatorHistoryActivity : AppCompatActivity(){
    private lateinit var calculatorHistoryController : CalculatorHistoryController
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculatorhistory)

        calculatorHistoryController =  CalculatorHistoryController(this.baseContext)

        val recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.historyList)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        calculatorHistoryController.loadList()
        val history : ArrayList<HistoryEntry> = calculatorHistoryController.getList()

        val historyAdapter : HistoryAdapter = HistoryAdapter(history)
        recyclerView.adapter = historyAdapter

        val btnReset: Button = findViewById<Button>(R.id.btn_clearHistory);
        btnReset.setOnClickListener {
            calculatorHistoryController.clearList()
            historyAdapter.notifyDataSetChanged()
        }
    }
}

