<h1 align="center">Fav Dish</h1>

<p align="center">
Fav Dish is a small demo application based on modern Android application tech-stacks and MVVM architecture.<br>Fav dish is an application where you can add your favorite recipes and share them with your friends.
<br>Care about was placed on the use of new technologies in this project.<br>
Also fetching data from the network and integrating persisted data in the database via repository pattern.
</p>

<p align="center">
<img src="/images/fav.png"/>
</p>


## Tech Stack & Open-Source Library
- Kotlin
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs network data.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
- Dexter - helps to get user permissions.
- Jetpack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room - construct a database using the abstract layer.
  - WorkManager - Schedule and execute deferrable, constraint-based background tasks.
  - Navigation - Build and structure your in-app UI, handle deep links, and navigate between screens.
 - Architecture
  -  MVVM Architecture (View - ViewModel - Model)
  -  Repository pattern
  - [Material-Components](https://github.com/material-components/material-components-android) - Material design components like cardView.
