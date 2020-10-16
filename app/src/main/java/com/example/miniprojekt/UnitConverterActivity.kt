package com.example.miniprojekt



import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import controller.UnitController
import kotlin.math.round


class UnitConverterActivity : AppCompatActivity(){
    private lateinit var spinnerFrom : Spinner
    private lateinit var spinnerTo : Spinner
    private lateinit var inputField : TextView
    private lateinit var outputField : TextView
    private var spinnerArray :ArrayList<String> = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unitconverter)

        val unitController :UnitController = UnitController()

        // Make MainSpinner
        val spinner = findViewById<View>(R.id.converting) as Spinner
        //spinner.onItemSelectedListener = this;
        val categories: ArrayList<String> = ArrayList()
        categories.add("Distance")
        categories.add("Temperature")
        val dataAdapter = ArrayAdapter(this, R.layout.customspinner, categories)
        spinner.adapter = dataAdapter;
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                unitController.spinnerMainState = position
                changeConverting(position)
            }
        }
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        setSpinnerToDistance()
        spinnerFrom.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                unitController.spinnerFromState = position
            }
        }
        spinnerTo.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                unitController.spinnerToState = position
            }
        }

        inputField = findViewById<TextView>(R.id.inputField)
        outputField = findViewById<TextView>(R.id.outputUnit)

        val enterButton = findViewById<Button>(R.id.btn_enter)
        enterButton.setOnClickListener {
            var inputDouble : Double = 0.0
            val inputText: String = inputField.text.toString()
            if (inputText.isNotEmpty()) {
                try {
                    inputDouble = inputText.toDouble()
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
            }
            val result = unitController.calcUnits(inputDouble)
            outputField.text = (round(result * 100) / 100).toString()
        }

    }


    private fun setSpinnerToDistance(){
        spinnerArray.clear()
        spinnerArray.add("Kilometer")
        spinnerArray.add("Meter")
        spinnerArray.add("Centimeter")
        spinnerArray.add("Millimeter")
        spinnerArray.add("inch")
        spinnerArray.add("foot")
        spinnerArray.add("yard")
        spinnerArray.add("mile")
        val dataAdapter1 = ArrayAdapter(this, R.layout.customspinner, spinnerArray)
        spinnerFrom.adapter = dataAdapter1;
        val dataAdapter2 = ArrayAdapter(this, R.layout.customspinner, spinnerArray)
        spinnerTo.adapter = dataAdapter2;
    }
    private fun setSpinnerToTemperature(){
        spinnerArray.clear()
        spinnerArray.add("Celsius")
        spinnerArray.add("Fahrenheit")
        spinnerArray.add("Kelvin")
        val dataAdapter1 = ArrayAdapter(this, R.layout.customspinner, spinnerArray)
        spinnerFrom.adapter = dataAdapter1;
        val dataAdapter2 = ArrayAdapter(this, R.layout.customspinner, spinnerArray)
        spinnerTo.adapter = dataAdapter2;
    }
    private fun changeConverting(position: Int){
        outputField.text = ""
        when(position){
            1 -> setSpinnerToTemperature()
            2 -> setSpinnerToDistance()
            else -> setSpinnerToDistance()
        }
    }
}