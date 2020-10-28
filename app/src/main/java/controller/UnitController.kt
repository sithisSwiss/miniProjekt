package controller

class UnitController {

    var spinnerFromState : Int = 0
    var spinnerToState : Int = 0
    var spinnerMainState : Int = 0

    fun calcUnits(input : Double) : Double{
        when(spinnerMainState){
            0 ->{ //Distance
                return calcDistance(input)
            }
            1 -> { //Temperature
                return calcTemperature(input)
            }
            else -> {
                return 666.0
            }
        }
    }
    private fun calcDistance(input : Double) : Double{


        // make Meter
        if(spinnerFromState == spinnerToState)
            return input
        var meter : Double = 0.0
        when(spinnerFromState){
            0 -> meter = input * 1000 // km -> m
            1 -> meter = input // m -> m
            2 -> meter = input / 100 // cm -> m
            3 -> meter = input / 1000 // mm -> m
            4 -> meter = input / 39.37 // inch -> m
            5 -> meter = input / 3.281 // foot -> m
            6 -> meter = input / 1.094 // yard -> m
            7 -> meter = input * 1609 // mile -> m
        }
        return when(spinnerToState){
            0 -> meter / 1000 // m -> km
            1 -> meter // m -> m
            2 -> meter * 100 // m -> cm
            3 -> meter * 1000 // m -> mm
            4 -> meter * 39.37 // m -> inch
            5 -> meter * 3.281 // m -> foot
            6 -> meter * 1.094 // m -> yard
            7 -> meter / 1609 // m -> mile
            else -> input
        }
    }


    private fun calcTemperature(input : Double) : Double{
        when(spinnerFromState){
            0 -> {//Celsius
                when(spinnerToState){
                    0 -> return input //Celsius -> Celsius
                    1 -> return input * (9/5) + 32 //Celsius -> Fahrenheit
                    2 -> return input + 273.15 //Celsius -> Kelvin
                }
            }
            1 -> { //Fahrenheit
                when(spinnerToState){
                    0 -> return (input - 32) * (5/9) //Fahrenheit -> Celsius
                    1 -> return input //Fahrenheit -> Fahrenheit
                    2 -> return (input - 32) * (5/9) + 273.15 //Fahrenheit -> Kelvin
                }
            }
            2 -> { //Kelvin
                when(spinnerToState){
                    0 -> return input - 273.15 //Kelvin -> Celsius
                    1 -> return (input -273.15) * (9/5) + 32 //Kelvin -> Fahrenheit
                    2 -> return input //Kelvin -> Kelvin
                }
            }
        }
        return 100.0
    }
}