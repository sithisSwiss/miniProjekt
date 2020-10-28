package com.example.miniprojekt



import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import controller.CalculatorController
import controller.CalculatorHistoryController
import controller.Operation



class CalculatorActivity : AppCompatActivity(), View.OnClickListener{
    private val btn :ArrayList<Button> = ArrayList()
    private val btnId = arrayOf (
        R.id.btn_0,
        R.id.btn_1,
        R.id.btn_2,
        R.id.btn_3,
        R.id.btn_4,
        R.id.btn_5,
        R.id.btn_6,
        R.id.btn_7,
        R.id.btn_8,
        R.id.btn_9,
        R.id.btn_dot,
        R.id.btn_add,
        R.id.btn_sub,
        R.id.btn_div,
        R.id.btn_mod,
        R.id.btn_mul,
        R.id.btn_pow,
        R.id.btn_sqrt,
        R.id.btn_enter,
        R.id.btn_c)
    private val calcController : CalculatorController = CalculatorController()
    private lateinit var history : CalculatorHistoryController
    private var outputString : String = ""


    private lateinit var outputField : TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        history = CalculatorHistoryController(this.baseContext)
        outputField = findViewById(R.id.result)

        for (i in btnId.indices) {
            btn.add(findViewById<Button>(btnId[i]))
            btn[i].setOnClickListener(this)
        }
        val historyBtn = findViewById<Button>(R.id.btn_history)
        historyBtn.setOnClickListener {
            val calcHistoryActivity : Intent = Intent(this, CalculatorHistoryActivity::class.java)
            startActivity(calcHistoryActivity)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_0 -> {
                calcController.inputHandlerNumber(0)
                outputString += "0"
            }
            R.id.btn_1 -> {
                calcController.inputHandlerNumber(1)
                outputString += "1"
            }
            R.id.btn_2 -> {
                calcController.inputHandlerNumber(2)
                outputString += "2"
            }
            R.id.btn_3 -> {
                calcController.inputHandlerNumber(3)
                outputString += "3"
            }
            R.id.btn_4 -> {
                calcController.inputHandlerNumber(4)
                outputString += "4"
            }
            R.id.btn_5 -> {
                calcController.inputHandlerNumber(5)
                outputString += "5"
            }
            R.id.btn_6 -> {
                calcController.inputHandlerNumber(6)
                outputString += "6"
            }
            R.id.btn_7 -> {
                calcController.inputHandlerNumber(7)
                outputString += "7"
            }
            R.id.btn_8 -> {
                calcController.inputHandlerNumber(8)
                outputString += "8"
            }
            R.id.btn_9 -> {
                calcController.inputHandlerNumber(9)
                outputString += "9"
            }
            R.id.btn_dot -> {
                calcController.inputHandlerOperation(Operation.Dot)
                outputString += "."
            }
            R.id.btn_add -> {
                calcController.inputHandlerOperation(Operation.Add)
                outputString += " + "
            }
            R.id.btn_sub -> {
                calcController.inputHandlerOperation(Operation.Sub)
                outputString += " - "
            }
            R.id.btn_div -> {
                calcController.inputHandlerOperation(Operation.Div)
                outputString += " / "
            }
            R.id.btn_mod -> {
                calcController.inputHandlerOperation(Operation.Mod)
                outputString += " % "
            }
            R.id.btn_mul -> {
                calcController.inputHandlerOperation(Operation.Mul)
                outputString += " * "
            }
            R.id.btn_pow -> {
                calcController.inputHandlerOperation(Operation.Pow)
                outputString += " ^ "
            }
            R.id.btn_sqrt -> {
                calcController.inputHandlerOperation(Operation.Sqrt)
                outputString += " ^1/ "
            }
            R.id.btn_c -> {
                calcController.inputHandlerOperation(Operation.Clear)
                outputString = ""
            }
            R.id.btn_history -> {
                val historyActivity : Intent = Intent(this, CalculatorHistoryActivity::class.java)
                startActivity(historyActivity)
            }

            R.id.btn_enter -> {
                outputString += " = "
                outputString += calcController.enterCalculation()
                history.addToList(outputString)
                outputField.text = outputString
                outputString = ""
                return
            }
        }
        outputField.text = outputString
    }
}