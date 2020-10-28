package controller


import java.net.URL
import org.json.JSONObject

class CurrencyConverterController() {

    class ExchangeRate {
        var mapAUD = hashMapOf("AUD" to 1.00, "CAD" to 0.94, "CHF" to 0.64, "EUR" to 0.60, "GBP" to 0.55,  "USD" to 0.71)
        var mapCAD = hashMapOf("AUD" to 1.06, "CAD" to 1.00, "CHF" to 0.69, "EUR" to 0.64, "GBP" to 0.58,  "USD" to 0.76)
        var mapCHF = hashMapOf("AUD" to 1.55, "CAD" to 1.45, "CHF" to 1.00, "EUR" to 0.93, "GBP" to 0.84,  "USD" to 1.10)
        var mapEUR = hashMapOf("AUD" to 1.66, "CAD" to 1.56, "CHF" to 1.07, "EUR" to 1.00, "GBP" to 0.91,  "USD" to 1.18)
        var mapGBP = hashMapOf("AUD" to 1.83, "CAD" to 1.72, "CHF" to 1.18, "EUR" to 1.10, "GBP" to 1.00,  "USD" to 1.30)
        var mapUSD = hashMapOf("AUD" to 1.40, "CAD" to 1.32, "CHF" to 0.91, "EUR" to 0.85, "GBP" to 0.77,  "USD" to 1.00)
        var mainMap = hashMapOf("AUD" to mapAUD, "CAD" to mapCAD, "CHF" to mapCHF, "EUR" to mapEUR, "GBP" to mapGBP, "USD" to mapUSD)
    }
    private val exchangeRate : ExchangeRate = ExchangeRate()
    fun getListOfCurrency() : ArrayList<String>{
        val list : ArrayList<String> = ArrayList()
        exchangeRate.mainMap.forEach { (key, _) ->
            list.add(key);
        }
        return list;
    }

    class GetCourseWithThread(private var exchangeRate : ExchangeRate) : Runnable{
        override fun run() {
            val tempMap  = HashMap<String, HashMap<String, Double>>() //Benötigt falls Teil der Abfrage Fehlschlägt, wird die komplette DefaultMap verwendet
            exchangeRate.mainMap.forEach{(key, maps) ->
                val temp = HashMap<String, Double>()
                maps.forEach { (k, d) ->
                    temp[k] = d
                }
                tempMap[key] = temp
            }

            try{
                tempMap.forEach{ (key, value) ->
                    val courseObj = JSONObject(URL("https://api.exchangeratesapi.io/latest?base=$key").readText()).getJSONObject("rates")
                    for(k in value.keys)
                        if(key != k)
                            value[k] = courseObj.getDouble(k)
                }
                exchangeRate.mainMap = HashMap(tempMap)
            }catch (ex : Exception) { "null" }
        }
    }
    private fun getAllCourse() {
        val thready = GetCourseWithThread(exchangeRate)
        Thread(thready).start()
    }

    init {
        getAllCourse()
    }
    fun getRate(from: String, to: String) : Double? {
        return exchangeRate.mainMap[from]?.get(to)
    }

}
