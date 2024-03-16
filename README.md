# Movies

A sample project created for demonstrating MVVM architecture.

## Description
This app shows the list of Movies in the list and its details when a movie item is clicked. This app is written following the 
recommended architecture guide by Google which is MVVM with Repository pattern, as this gives the opportunity 
to write clean and concise code with clear separation of concerns. 

## Run
This app uses The Movie Db (https://themoviedb.org) api to fetch the movies in real time.
To run this app, you will need to add your The Movie Db api_key in local.properties

## Tech stack & Open-source libraries
- Architecture
    - MVVM Architecture
    - Repository Pattern
- Kotlin based, Coroutines + Flow for asynchronous.
- Hilt for dependency injection.
- Jetpack
    - Lifecycle
    - ViewModel
    - ViewBinding
- Retrofit 2 and OkHttp for networking
- Gson for JSON mapping to objects
- Glide for Image loading and caching
- Timber


