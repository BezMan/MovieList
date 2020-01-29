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
        val gson = Gson()
        val type = object : TypeToken<List<String>?>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    @JvmStatic
    fun listToJson(value: MutableList<Movie>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String?): MutableList<Movie> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Movie>?>() {}.type
        return gson.fromJson(value, type)
    }

}
