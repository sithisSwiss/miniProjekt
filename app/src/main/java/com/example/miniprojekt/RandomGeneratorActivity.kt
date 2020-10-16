package com.example.miniprojekt

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import controller.RandomGeneratorController
import controller.UnitController
import kotlin.math.round

class RandomGeneratorActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager

    private val sensorValueField :ArrayList<TextView> = ArrayList()
    private val valueListAccelerometer : ArrayList<Float> = ArrayList()
    private val valueListMagneticField : ArrayList<Float> = ArrayList()
    private val valueListGyroscope : ArrayList<Float> = ArrayList()
    private var valueLight :Float = 0F

    private lateinit var checkBoxAccelerometer:CheckBox
    private lateinit var checkBoxMagneticField:CheckBox
    private lateinit var checkBoxGyroscope:CheckBox
    private lateinit var checkBoxLight:CheckBox
    private lateinit var checkBoxSystemTime:CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomgenerator)

        val randomGenerator : RandomGeneratorController = RandomGeneratorController()

        val fromField:EditText = findViewById<EditText>(R.id.randomInput1)
        val toField:EditText = findViewById<EditText>(R.id.randomInput2)
        val randomOutput : TextView = findViewById<TextView>(R.id.randomOutput)
        val generateBtn:Button = findViewById(R.id.randomEnter)

        sensorValueField.add(findViewById(R.id.sensorValueAccelerometer))
        sensorValueField.add(findViewById(R.id.sensorValueMagneticField))
        sensorValueField.add(findViewById(R.id.sensorValueGyroscope))
        sensorValueField.add(findViewById(R.id.sensorValueLight))

        checkBoxAccelerometer = findViewById(R.id.checkboxAccelerometer)
        checkBoxMagneticField = findViewById(R.id.checkboxMagneticField)
        checkBoxGyroscope = findViewById(R.id.checkboxGyroscope)
        checkBoxLight = findViewById(R.id.checkboxLight)
        checkBoxSystemTime = findViewById(R.id.checkboxSystemTime)


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
                accelerometer -> sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also {
                magneticField -> sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also {
                gyroscope -> sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)?.also {
                light -> sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

        generateBtn.setOnClickListener {
            var fromInt : Int = 0
            var toInt : Int = 0
            val fromText: String = fromField.text.toString()
            val toText: String = toField.text.toString()
            if (fromText.isNotEmpty() && toText.isNotEmpty()) {
                try {
                    fromInt = fromText.toInt()
                    toInt = toText.toInt()
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
            }
            val valueList : ArrayList<Float> = ArrayList()
            valueList.addAll(valueListAccelerometer)
            valueList.addAll(valueListMagneticField)
            valueList.addAll(valueListGyroscope)
            valueList.add(valueLight)
            var seed : Long = randomGenerator.makeSeed(valueList)
            if(checkBoxSystemTime.isChecked)
                seed *= System.nanoTime()
            randomOutput.text = randomGenerator.generate(fromInt, toInt, seed).toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) {
            return
        }
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            sensorValueField[0].text = "${event.values[0]}\n${event.values[1]}\n${event.values[2]}"
            valueListAccelerometer.clear()
            if(checkBoxAccelerometer.isChecked){
                valueListAccelerometer.add(event.values[0])
                valueListAccelerometer.add(event.values[1])
                valueListAccelerometer.add(event.values[2])
            }
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            sensorValueField[1].text = "${event.values[0]}\n${event.values[1]}\n${event.values[2]}"
            valueListMagneticField.clear()
            if(checkBoxMagneticField.isChecked){
                valueListMagneticField.add(event.values[0])
                valueListMagneticField.add(event.values[1])
                valueListMagneticField.add(event.values[2])
            }
        }else if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            sensorValueField[2].text = "${event.values[0]}\n${event.values[1]}\n${event.values[2]}"
            valueListGyroscope.clear()
            if(checkBoxGyroscope.isChecked){
                valueListGyroscope.add(event.values[0])
                valueListGyroscope.add(event.values[1])
                valueListGyroscope.add(event.values[2])
            }
        }else if (event.sensor.type == Sensor.TYPE_LIGHT) {
            sensorValueField[3].text = "\n${event.values[0]}\n"
            if(checkBoxLight.isChecked)
                valueLight = event.values[0]
        }
    }
}