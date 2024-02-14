package it.saimao.shanproverbs.data

import android.content.Context
import com.google.gson.Gson
import it.saimao.shanproverbs.R
import java.io.BufferedReader
import java.io.InputStreamReader

data class AllProverbs(
    val allProverbs: List<Proverbs>
)

data class Proverbs(
    val key: String,
    val proverbs: List<Proverb>
)

data class Proverb(
    val proverb: String
) {
    override fun toString(): String {
        return proverb
    }
}

data object Jsons {
    private var jsonData: AllProverbs? = null
    fun getJsonData(context: Context): AllProverbs {

        if (jsonData == null) {
            val inputStream = context.resources.openRawResource(R.raw.shan_proverbs)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.readText()

            // Parse the JSON string using Gson
            val gson = Gson()
            jsonData = gson.fromJson(jsonString, AllProverbs::class.java)
        }
        // Access data from the parsed object
        return jsonData!!
    }
}