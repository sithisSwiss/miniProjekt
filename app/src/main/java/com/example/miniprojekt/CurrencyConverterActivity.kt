package com.example.miniprojekt

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import controller.CurrencyConverterController
import kotlin.math.round

class CurrencyConverterActivity : AppCompatActivity(){
    private val currencyConverter : CurrencyConverterController = CurrencyConverterController()
    private var x : Int = 0
    private val positionsMap = currencyConverter.getListOfCurrency().associateBy(keySelector = {x++;}, valueTransform = { i-> i})
    private var positionSpinnerFrom : String = positionsMap[0] ?: error("position is NULL")
    private var positionSpinnerTo : String = positionsMap[0] ?: error("position is NULL")

    private lateinit var exchangeFrom:TextView
    private lateinit var exchangeTo:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencyconverter)


        // Make Spinners
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom) as Spinner
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo) as Spinner

        exchangeFrom = findViewById<TextView>(R.id.exchangeFrom)
        exchangeTo = findViewById<TextView>(R.id.exchangeTo)
        val inputField = findViewById<EditText>(R.id.exchangeCalcIn)
        val outputField = findViewById<TextView>(R.id.exchangeCalcOut)
        val calcBtn = findViewById<Button>(R.id.btnCalc)

        val categories: ArrayList<String> = currencyConverter.getListOfCurrency()


        val dataAdapter = ArrayAdapter(this, R.layout.customspinner, categories)
        spinnerFrom.adapter = dataAdapter;
        spinnerTo.adapter = dataAdapter;
        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionSpinnerTo = setPosition(position)
                changeRateDisplay()
            }
        }
        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionSpinnerFrom = setPosition(position)
                changeRateDisplay()
            }
        }
        calcBtn.setOnClickListener{
            var inputDouble : Double = 0.0
            val inputText: String = inputField.text.toString()
            if (inputText.isNotEmpty()) {
                try {
                    inputDouble = inputText.toDouble()
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
            }
            val result : Double = (round(inputDouble* currencyConverter.getRate(positionSpinnerTo, positionSpinnerFrom)!! * 10000) / 10000)
            outputField.text = (result).toString()
        }

    }
    fun setPosition(pos:Int):String{
        return positionsMap[pos] ?: error("position is NULL")
    }
    @SuppressLint("SetTextI18n")
    private fun changeRateDisplay(){

        exchangeFrom.text = "1 $positionSpinnerTo = ${currencyConverter.getRate(positionSpinnerTo, positionSpinnerFrom)} $positionSpinnerFrom"
        exchangeTo.text = "1 $positionSpinnerFrom = ${currencyConverter.getRate(positionSpinnerFrom, positionSpinnerTo)} $positionSpinnerTo"

    }


}