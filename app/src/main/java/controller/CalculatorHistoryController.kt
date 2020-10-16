package controller

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import data.HistoryEntry
import java.time.LocalDateTime

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.time.format.DateTimeFormatter
import java.security.AccessControlContext


class CalculatorHistoryController(private val context: Context) {
    private val historyList : ArrayList<HistoryEntry> = ArrayList();
    private val folder : File = context.filesDir;
    private val file : File = File(folder, "historyFile.txt")



    fun getList() : ArrayList<HistoryEntry>{
        return historyList
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addToList(input: String){
        val timeStamp = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy / HH:mm:ss")
        val formatted = timeStamp.format(formatter)

        val historyEntry = HistoryEntry(input, formatted.toString())
        loadList()
        historyList.add(historyEntry)

        //var type : Type = TypeToken<Collection<HistoryEntry>>(){}.getType()
        val jsonString: String = Gson().toJson(historyList)
        Log.d("customADD", jsonString)
        val outputStream : FileOutputStream = FileOutputStream(file)
        outputStream.write(jsonString.toByteArray(Charsets.UTF_8))
        outputStream.close()
    }
    fun clearList(){
        historyList.clear()
        val outputStream : FileOutputStream = FileOutputStream(file)
        outputStream.write("[]".toByteArray(Charsets.UTF_8))
        outputStream.close()
    }
    fun loadList(){
        Log.d("customLOAD", "Filename: ${file.name}\n" +
                "Length: ${file.length()}\n" +
                "Path: ${file.path}")
        val bytes = ByteArray(file.length().toInt())
        val inputStream : FileInputStream = FileInputStream(file)
        inputStream.read(bytes)
        inputStream.close()
        Log.d("customLOAD", "Bytes: $bytes")
        val jsonString : String = String(bytes)
        Log.d("customLOAD", "JSON: $jsonString")
        historyList.clear()
        val turnsType = object : TypeToken<List<HistoryEntry>>() {}.type
        val test : List<HistoryEntry> = Gson().fromJson<List<HistoryEntry>>(jsonString, turnsType)
        Log.d("customLOAD", test.toString())

        historyList.addAll(test)



    }
}