package controller

import android.util.Log
import java.net.URL
import org.json.JSONException
import org.json.JSONObject

class CurrencyConverterController() {

    class ExchangeRate {
        var EUR_USD: Double = 0.0
        var USD_EUR: Double = 0.0
        var EUR_CHF: Double = 0.0
        var CHF_EUR: Double = 0.0
        var CHF_USD: Double = 0.0
        var USD_CHF: Double = 0.0
    }
    val exchangeRate : ExchangeRate = ExchangeRate()
    class GetCourseWithThread(private val exchangeRate: ExchangeRate) : Runnable{
        override fun run() {
            var courseObj = JSONObject(URL("https://free.currconv.com/api/v7/convert?apiKey=e6428da7835a5d437217&compact=ultra&q=EUR_CHF,CHF_EUR").readText())
            exchangeRate.EUR_CHF = courseObj.getDouble("EUR_CHF")
            exchangeRate.CHF_EUR = courseObj.getDouble("CHF_EUR")
            courseObj = JSONObject(URL("https://free.currconv.com/api/v7/convert?apiKey=e6428da7835a5d437217&compact=ultra&q=EUR_USD,USD_EUR").readText())
            exchangeRate.EUR_USD = courseObj.getDouble("EUR_USD")
            exchangeRate.USD_EUR = courseObj.getDouble("USD_EUR")
            courseObj = JSONObject(URL("https://free.currconv.com/api/v7/convert?apiKey=e6428da7835a5d437217&compact=ultra&q=CHF_USD,USD_CHF").readText())
            exchangeRate.CHF_USD = courseObj.getDouble("CHF_USD")
            exchangeRate.USD_CHF = courseObj.getDouble("USD_CHF")
        }
    }
    private fun getAllCourse() {
        val thready = GetCourseWithThread(exchangeRate)
        Thread(thready).start()
    }

    init {
        getAllCourse()
    }

}
