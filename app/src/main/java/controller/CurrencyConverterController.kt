package controller

import android.util.Log
import android.widget.TextView
import com.example.miniprojekt.CurrencyConverterActivity
import java.net.URL
import org.json.JSONException
import org.json.JSONObject

class CurrencyConverterController() {

    class ExchangeRate {
        var mapAUD = hashMapOf("AUD" to 1.0, "CAD" to 0.0, "CHF" to 0.0, "EUR" to 0.0, "GBP" to 0.0,  "USD" to 0.0)
        var mapCAD = hashMapOf("AUD" to 0.0, "CAD" to 1.0, "CHF" to 0.0, "EUR" to 0.0, "GBP" to 0.0,  "USD" to 0.0)
        var mapCHF = hashMapOf("AUD" to 0.0, "CAD" to 0.0, "CHF" to 1.0, "EUR" to 0.0, "GBP" to 0.0,  "USD" to 0.0)
        var mapEUR = hashMapOf("AUD" to 0.0, "CAD" to 0.0, "CHF" to 0.0, "EUR" to 1.0, "GBP" to 0.0,  "USD" to 0.0)
        var mapGBP = hashMapOf("AUD" to 0.0, "CAD" to 0.0, "CHF" to 0.0, "EUR" to 0.0, "GBP" to 1.0,  "USD" to 0.0)
        var mapUSD = hashMapOf("AUD" to 0.0, "CAD" to 0.0, "CHF" to 0.0, "EUR" to 0.0, "GBP" to 0.0,  "USD" to 1.0)
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
            exchangeRate.mainMap.forEach{ (key, value) ->
                val courseObj = JSONObject(URL("https://api.exchangeratesapi.io/latest?base=$key").readText()).getJSONObject("rates");
                for(k in value.keys)
                    if(key != k)
                        value[k] = courseObj.getDouble(k)
            }
        }
    }
    /*class GetCourseWithThread(private val exchangeRate: ExchangeRate) : Runnable{
        override fun run() {
            var courseObj = JSONObject(URL("https://free.currconv.com/api/v7/convert?apiKey=a564c04c3775a96ec107&compact=ultra&q=EUR_CHF,CHF_EUR").readText())
            exchangeRate.EUR_CHF = courseObj.getDouble("EUR_CHF")
            exchangeRate.CHF_EUR = courseObj.getDouble("CHF_EUR")
            courseObj = JSONObject(URL("https://free.currconv.com/api/v7/convert?apiKey=a564c04c3775a96ec107&compact=ultra&q=EUR_USD,USD_EUR").readText())
            exchangeRate.EUR_USD = courseObj.getDouble("EUR_USD")
            exchangeRate.USD_EUR = courseObj.getDouble("USD_EUR")
            courseObj = JSONObject(URL("https://free.currconv.com/api/v7/convert?apiKey=a564c04c3775a96ec107&compact=ultra&q=CHF_USD,USD_CHF").readText())
            exchangeRate.CHF_USD = courseObj.getDouble("CHF_USD")
            exchangeRate.USD_CHF = courseObj.getDouble("USD_CHF")



        }
    }*/
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
