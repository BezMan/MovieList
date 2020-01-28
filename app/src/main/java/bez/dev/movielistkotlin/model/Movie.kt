package bez.dev.movielistkotlin.model

data class Movie(
    val title: String,
    val image: String,
    val rating: Double,
    val releaseYear: Int,
    val genre: List<String>
)

