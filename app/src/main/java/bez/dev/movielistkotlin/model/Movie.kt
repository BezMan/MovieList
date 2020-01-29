package bez.dev.movielistkotlin.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey val title: String,
    val image: String,
    val rating: Double,
    val releaseYear: Int,
    val genre: List<String>
) : Parcelable


