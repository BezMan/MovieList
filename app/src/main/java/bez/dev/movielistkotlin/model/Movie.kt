package bez.dev.movielistkotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val image: String,
    val rating: Double,
    val releaseYear: Int,
    val genre: List<String>
) : Parcelable

