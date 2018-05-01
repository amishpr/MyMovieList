# MyMovieList (CMPSC 475 Final Project) by Amish P.
## Overview
An Android app that allows you to track the movies that you have watched, plan to watch, or have dropped.

## APIs Used
### TMDb API
https://www.themoviedb.org/documentation/api

Used to get all information about the movies. Movies are searched using the API. The API returns a JSON Array which is parsed to a Movie object. The Movie object includes information such as id, title, release date, link to the movie's poster, etc.

### Gson
https://github.com/google/gson

Used to convert Java objects to JSON objects. In my case, I used to convert my Movie object to a JSON object. This JSON object was stored in SharedPreferences so the movie list could be stored locally.

## SQLite database

This app includes a login and register activity. User account information is stored in a SQLite database.

## SharedPreferences

SharedPreferences is used to both store the user's movie list and to store what background color they have selected in their setting.

## AsyncTask

In SearchActivity.java, a custom AsyncTask is used to retrieve and process the JSON array that is returned from TMDb's API.
