package controller

import android.util.Log
import kotlin.math.abs
import kotlin.random.Random

class RandomGeneratorController {
    fun generate(from : Int, to : Int, seed : Long) : Int{
        var fromC = from
        var toC = to
        if(from>to) {
            fromC = to
            toC = from
        }
        if(toC-fromC + 1 > Int.MAX_VALUE){
            fromC = 1
            toC = Int.MAX_VALUE -1
        }
        return Random(seed).nextInt(fromC, toC)
    }
    fun makeSeed(listOfValue : ArrayList<Float>) : Long{
        var seed : Double = 1.0
        for(item in listOfValue){
            if(item !in -0.5..0.5)
                seed *= item
        }
        return ((abs(seed) * 1000).toInt() % Int.MAX_VALUE).toLong()
    }
}
