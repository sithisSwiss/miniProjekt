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
    enum class Currency {
        CHF, EUR, USD
    }
    private var positionSpinnerFrom: Currency = Currency.CHF
    private var positionSpinnerTo: Currency = Currency.CHF

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

        val categories: ArrayList<String> = ArrayList()
        categories.add("CHF")
        categories.add("EUR")
        categories.add("USD")
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
            val result : Double = (round(inputDouble*getRate(positionSpinnerTo, positionSpinnerFrom) * 10000) / 10000)
            outputField.text = (result).toString()
        }

    }
    fun setPosition(pos:Int):Currency{
        return when(pos){
            0->Currency.CHF
            1->Currency.EUR
            2->Currency.USD
            else -> Currency.CHF
        }
    }
    @SuppressLint("SetTextI18n")
    private fun changeRateDisplay(){

        exchangeFrom.text = "1 ${positionSpinnerTo.name} = ${getRate(positionSpinnerTo, positionSpinnerFrom)} ${positionSpinnerFrom.name}"
        exchangeTo.text = "1 ${positionSpinnerFrom.name} = ${getRate(positionSpinnerFrom, positionSpinnerTo)} ${positionSpinnerTo.name}"

    }
    private fun getRate(from:Currency, to:Currency) : Double{
        if(from == to){
            return 1.0
        }else if(from == Currency.CHF && to == Currency.EUR){
            return currencyConverter.exchangeRate.CHF_EUR
        }else if(from == Currency.CHF && to == Currency.USD){
            return currencyConverter.exchangeRate.CHF_USD
        }else if(from == Currency.EUR && to == Currency.CHF){
            return currencyConverter.exchangeRate.EUR_CHF
        }else if(from == Currency.EUR && to == Currency.USD){
            return currencyConverter.exchangeRate.EUR_USD
        }else if(from == Currency.USD && to == Currency.CHF){
            return currencyConverter.exchangeRate.USD_CHF
        }else if(from == Currency.USD && to == Currency.EUR){
            return currencyConverter.exchangeRate.USD_EUR
        }else{
            return 1.0
        }
    }

}