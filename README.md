# MovieList

This app is for learning purposes, written in Kotlin and showcasing MVVM architecture.

- Sending network request using Retrofit (https://api.androidhive.info/json/movies.json).
- Parsing JSON data and storing in Room database, which will be out single source of truth for UI updates.
- Repository pattern, responsible for network and database calls, considering internet connectivity.
- Converters class for saving objects and lists into Room DB.
- Maintaining state of list and search filter, upon device rotation.
- Querying database for movies, ordered by releaseYear.
- SearchView for filtering results by title.
- Pull to refresh and send network request. 

<br/>

<div class="row">
<img src="images/screenshots/scr1.jpeg" width="250">
<img src="images/screenshots/scr2.jpeg" width="250">
<img src="images/screenshots/scr3.jpeg" width="250">
</div>

<br/>

# Flow
Entering the app fetches fresh network data and save to our internal DB. If no network connection, fallback to the last saved data.

Navigating to DetailActivity can happen without network connection because image is auto-cached by Glide.

# ENJOY :)