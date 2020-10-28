package com.example.miniprojekt


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnCalc:Button = findViewById<Button>(R.id.btn_calc);
        val btnCurrency:Button = findViewById<Button>(R.id.btn_currency);
        val btnUnit:Button = findViewById<Button>(R.id.btn_unit);
        val btnRandom:Button = findViewById<Button>(R.id.btn_random);
        btnCalc.setOnClickListener {
            val calcActivity : Intent = Intent(this, CalculatorActivity::class.java)
            startActivity(calcActivity)
        }
        btnCurrency.setOnClickListener {
            val currencyActivity : Intent = Intent(this, CurrencyConverterActivity::class.java)
            startActivity(currencyActivity)
        }
        btnUnit.setOnClickListener {
            val unitActivity : Intent = Intent(this, UnitConverterActivity::class.java)
            startActivity(unitActivity)
        }
        btnRandom.setOnClickListener {
            val randomActivity : Intent = Intent(this, RandomGeneratorActivity::class.java)
            startActivity(randomActivity)
        }

    }






}