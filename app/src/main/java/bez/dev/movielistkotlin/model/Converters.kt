package bez.dev.movielistkotlin.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object Converters {

    @TypeConverter
    @JvmStatic
    fun genreToString(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun genreStrToList(value: String?): List<String> {
        val type = object : TypeToken<List<String>?>() {}.type
        return Gson().fromJson(value, type)
    }



}
