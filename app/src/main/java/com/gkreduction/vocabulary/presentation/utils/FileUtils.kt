package com.gkreduction.vocabulary.presentation.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.*


data class BaseJsonFile(
    var words: List<BaseWord.Word>,
    var verbs: List<BaseWord.IrVerb>,
    var idioms: List<BaseWord.Idiom>
)

fun getJsonDataFromAsset(context: Context, uri: Uri): String? {
    val jsonString: String?
    var inputStream: InputStream? = null
    try {
        inputStream = context.contentResolver.openInputStream(uri)
        jsonString = BufferedReader(InputStreamReader(inputStream)).readText()
        inputStream?.close()

    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

    return jsonString
}

fun getDataBdByUri(context: Context, uri: Uri): List<BaseWord> {
    val jsonFileString = getJsonDataFromAsset(context, uri)
    val gson = Gson()
    val baseJsonType = object : TypeToken<BaseJsonFile>() {}.type
    val baseJson: BaseJsonFile = gson.fromJson(jsonFileString, baseJsonType)

    val list = ArrayList<BaseWord>().apply {
        addAll(baseJson.idioms)
        addAll(baseJson.words)
        addAll(baseJson.verbs)
    }
    return list
}

fun saveBdToFileJson(base: List<BaseWord>, context: Context) {
    val idiom = ArrayList<BaseWord.Idiom>()
    val word = ArrayList<BaseWord.Word>()
    val verb = ArrayList<BaseWord.IrVerb>()
    base.forEach {
        when (it) {
            is BaseWord.Word -> word.add(it)
            is BaseWord.Idiom -> idiom.add(it)
            is BaseWord.IrVerb -> verb.add(it)
        }
    }

    val baseJson = BaseJsonFile(words = word, idioms = idiom, verbs = verb)
    val gson = GsonBuilder().create()
    val string = gson.toJson(baseJson, BaseJsonFile::class.java)
    val folder = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            .toString() + File.separator + "Vocabulary" + File.separator
    )
    if (!folder.exists()) {
        folder.mkdir()
    }
    val file = File(folder, "vocabulary.json")
    if (!file.exists())
        file.createNewFile()

    val stream = FileOutputStream(file)
    stream.use { it ->
        it.write(string.toByteArray())

    }

}
