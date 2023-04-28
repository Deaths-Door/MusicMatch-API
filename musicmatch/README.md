# Music Match

MusixMatch API is a robust service that provides music metadata, lyrics, and album art. It can be used to build music applications that require this data.

## Installation

To use the library, add the following dependency to your app's `build.gradle` file:

```kotlin
repositories {
   google()
   mavenCentral()
   maven { url 'https://jitpack.io'}          
}
```

### Using Gradle

```kotlin
implementation 'com.github.Deaths-Door:Galaxy-API-Collection:musicmatch:${latestVersion}'
```

### Using Kotlin DSL (.kst gradle files)

```kotlin
implementation("com.github.Deaths-Door:Galaxy-API-Collection:musicmatch:${latestVersion}")
```

Note: Replace ${`latestVersion`} with the latest version of the API module.

## Usage 

The main class to interact with the Music Match API is **MusixAbgleich** that takes an `apiKey` parameter.

```kotlin
val musixAbgleich = MusixAbgleich(apiKey)
```

### Enums

#### `ChartName`

The `ChartName` enum represents the different types of charts that can be requested through the Music Match API. Each value corresponds to a different chart and has a `String` representation that can be used in API requests. 

##### Values

- `Top`: Represents the top chart.
- `Hot`: Represents the hot chart.
- `MostViewedLyricsInLastSiebenTag`: Represents the most viewed lyrics in the last 7 days chart.
- `MostViewedLyricsInLastSiebenTagOfNewReleases`: Represents the most viewed lyrics in the last 7 days of new releases chart.

#### `PopularityIndex`

The `PopularityIndex` enum represents the ascending and descending popularity indexes that can be used in API requests to sort data. Each value has a `String` representation that can be used in API requests.

##### Values

- `Ascending`: Represents the ascending popularity index.
- `Descending`: Represents the descending popularity index.


### Dataclasses

#### `MusixGenre`

It represents a music genre returned by the API. It has the following properties:
/*
- id (Int): The ID of the music genre. This field can be used to identify a specific music genre when making API requests.
parentId (Int): The ID of the parent music genre, if any. This field can be used to determine the hierarchy of a music genre and group related genres together.

-name (String): The name of the music genre. This field can be used to display the name of the music genre to end-users in a music application.

-nameExtended (String): The extended name of the music genre. This field can be used to provide additional information about the music genre, such as its sub-genres or related genres.

-vanity (String?): The vanity name of the music genre, if any. This field can be used to provide a more user-friendly name for the music genre, such as a shortened or simplified version of the name. If no vanity name is available, this field will be null.

#### `MusixLyrics` 
It represents the lyrics of a music track returned by the API. It has the following properties:


-`id` (`Int`): The ID of the lyrics. This field can be used to identify a specific lyrics when making API requests.

-`isRestricted` (`Boolean, default: false`): A flag indicating whether the lyrics are restricted or not. If the lyrics are restricted, they may contain explicit content or may be protected by copyright laws.

-`isInstrumental` (`Boolean, default: false`): A flag indicating whether the track is instrumental or not.

-`isExplicit` (`Int`): A flag indicating whether the lyrics contain explicit content or not.

-`lyrics` (`String`): The actual lyrics of the track.
language (`String, default: "US"`): The language of the lyrics. This field can be used to display the language of the lyrics to end-users in a music application.

-`scriptTrackingURL` (`String, default: ""`): The URL of the script tracking for the lyrics.

-`pixelTrackingURL` (`String, default: ""`): The URL of the pixel tracking for the lyrics.

-`copyRight` (`String`): The copyright information of the lyrics.

-`backLinkURL` (`String, default: ""`): The backlink URL of the lyrics.

-`updatedTime` (`String`): The timestamp when the lyrics were last updated.
*/
### Member Functions

`topTracksOfCountry` i nteracts with the chart.tracks.get endpoint and returns a `List<MusixTrack>?` containing the top tracks of a specific country based on a certain chart.

```kotlin
musixAbgleich.topTracksOfCountry(chart: MusixAbgleich.Companion.ChartName, page: Int, pageSize: Int, country: String, hasLyrics: Boolean): List<MusixTrack>?
```

Parameters:
/*
-`chart`: Required parameter of type `MusixAbgleich.Companion.ChartName`
-page: Required parameter of type Int. It is used to specify the page number of the results to retrieve.
-pageSize: Required parameter of type Int. It is used to specify the number of tracks to retrieve per page.
-country: Required parameter of type String. It is used to specify the country for which to retrieve the top tracks. The country should be specified using its ISO 3166-1 alpha-2 code (e.g., "us" for the United States, "gb" for Great Britain, "ca" for Canada, etc.).
-hasLyrics: Optional parameter of type Boolean with a default value of true. It is used to specify whether to retrieve only tracks that -have lyrics available (true) or all tracks regardless of whether they have lyrics or not (false).
*/



`trackLyricsByTrackID` interacts with the endpoint `track.lyrics.get` and returns a `MusixLyrics?` containing lyrics of the song. It accepts `trackID` as a required parameter.

```kotlin
musixAbgleich.trackLyricsByCommonTrackID(/*Give Paramters here*/) 
```

`trackLyricsByCommonTrackID` interacts with the endpoint `track.lyrics.get` and returns a `MusixLyrics?` containing lyrics of the song. It accepts `commonTrackID` as a required parameter.

```kotlin
musixAbgleich.trackLyricsByCommonTrackID(/*Give Paramters here*/) 
```

`musicGenresInCatalogue` interacts with the endpoint `chart.artists.get` and returns a `List<MusixGenre>?` containing music genres present in the catalog.

```kotlin
musixAbgleich.musicGenresInCatalogue() 
```