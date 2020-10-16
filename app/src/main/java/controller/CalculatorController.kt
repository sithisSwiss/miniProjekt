package controller

import java.sql.Struct
import kotlin.math.pow

enum class Operation {
    Dot, Enter, Add, Sub, Div, Mod, Mul, Pow, Sqrt, Clear, None
}

class CalculatorController {

    private var activeOperation :Operation = Operation.None
    private var numberString : String = "0"
    private var firstNumber : Double = 0.0


    fun inputHandlerNumber(input :Int){
        numberString += input.toString()
    }
    fun inputHandlerOperation(input : Operation){
        when(input){
            Operation.Dot -> {
                numberString += "."
                return
            }
            Operation.Add -> activeOperation = input
            Operation.Sub -> activeOperation = input
            Operation.Div -> activeOperation = input
            Operation.Mod -> activeOperation = input
            Operation.Mul -> activeOperation = input
            Operation.Pow -> activeOperation = input
            Operation.Sqrt -> activeOperation = input
            Operation.Clear -> {
                activeOperation = Operation.None
                numberString = "0"
                firstNumber = 0.0
                return
            }
            else -> {}
        }
        firstNumber = numberString.toDouble()
        numberString = "0"

    }
    fun enterCalculation() : String{
        var result : Double = 0.0
        when(activeOperation){
            Operation.Add -> result = firstNumber + numberString.toDouble()
            Operation.Sub -> result = firstNumber - numberString.toDouble()
            Operation.Div -> result = firstNumber / numberString.toDouble()
            Operation.Mod -> result = firstNumber % numberString.toDouble()
            Operation.Mul -> result = firstNumber * numberString.toDouble()
            Operation.Pow -> result = firstNumber.pow(numberString.toDouble())
            Operation.Sqrt -> result = firstNumber.pow(1/numberString.toDouble())
            else ->{}
        }
        numberString = "0"
        firstNumber = 0.0
        activeOperation = Operation.None
        return result.toString()
    }
}