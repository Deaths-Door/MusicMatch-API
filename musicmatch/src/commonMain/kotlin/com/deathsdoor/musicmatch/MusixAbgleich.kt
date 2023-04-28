package com.deathsdoor.musicmatch

import com.deathsdoor.ktor_request_utils.Endpoint
import com.deathsdoor.ktor_request_utils.RequestHandlerWrapper
import com.deathsdoor.ktor_request_utils.RequestParameter
import com.deathsdoor.ktor_request_utils.hasToBeInRange
import com.deathsdoor.musicmatch.dataclasses.*
import com.deathsdoor.musicmatch.enums.ChartName
import com.deathsdoor.musicmatch.enums.PopularityIndex
import com.deathsdoor.musicmatch.enums.SubtitleFormat
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject


//TODO update module to use latest versions like the shazam,ticketmaster dataclasses
class MusixAbgleich constructor(override val apiKey: String) : RequestHandlerWrapper(){
    override val baseURL: String = "https://api.musixmatch.com/ws/1.1/"

    private abstract class ApiEndpoint {
        object MusicGenresGet: Endpoint("music.genres.get")

        object TrackLyricsMoodGet : Endpoint("track.lyrics.mood.get")

        object TrackSnippetGet : Endpoint("track.snippet.get")

        object TrackLyricsGet : Endpoint("track.lyrics.get")
        object TrackLyricsPost : Endpoint("track.lyrics.post")

        object TrackSubtitleGet : Endpoint("track.subtitle.get")

        object TrackGet : Endpoint("track.get")
        object TrackSearch : Endpoint("track.search")

        object ChartTracksGet : Endpoint("chart.tracks.get")

        object MatcherTrackGet : Endpoint("matcher.track.get")

        object AlbumGet : Endpoint("album.get")
        object AlbumTrackGet : Endpoint("album.tracks.get")

        object ArtistGet : Endpoint("artist.get")
        object ArtistSearch : Endpoint("artist.search")
        object ArtistAlbumsGet : Endpoint("artist.albums.get")
        object ArtistRelatedGet : Endpoint("artist.related.get")
    }
    private abstract class ApiRequestParameters {
        object CommonTrackId : RequestParameter("commontrack_id")
        object TrackId : RequestParameter("track_id")
        object TrackISRC : RequestParameter("track_isrc")

        object LyricsBody : RequestParameter("lyrics_body")

        object SubtitleFormat : RequestParameter("subtitle_format")

        object FilterSubtitleLength : RequestParameter("f_subtitle_length")
        object FilterLengthMaxDeviation : RequestParameter("f_subtitle_length_max_deviation")
        object FilterHasLyrics : RequestParameter("f_has_lyrics")

        object FilterArtistID : RequestParameter("f_artist_id")
        object FilterMusicBrainzArtistID : RequestParameter("f_artist_mbid")
        object FilterMusicGenreID : RequestParameter("f_music_genre_id")
        object FilterLyricsLanguage : RequestParameter("f_lyrics_language")
        object FilterTrackReleaseDataMax : RequestParameter("f_track_release_group_first_release_date_min")
        object FilterTrackReleaseDataMin : RequestParameter("f_track_release_group_first_release_date_max")

        object Country : RequestParameter("country")

        object Page : RequestParameter("page")
        object PageSize : RequestParameter("page_size")

        object ChartName : RequestParameter("chart_name")

        object QuorumFactor : RequestParameter("quorum_factor")

        object SortByArtistRating : RequestParameter("s_artist_rating")
        object SortByTrackRating : RequestParameter("s_track_rating")

        object QueryTrack : RequestParameter("q_track")
        object QueryArtist : RequestParameter("q_artist")
        object QueryAlbum: RequestParameter("q_album")
        object QueryLyrics : RequestParameter("q_lyrics")
        object QueryTrackArtist : RequestParameter("q_track_artist")
        object QueryWriter : RequestParameter("q_writer")
        object QueryAlle : RequestParameter("q")

        object AlbumID : RequestParameter("album_id")
        object MusicbrainzAlbumID : RequestParameter("album_mbid")


        object ArtistID : RequestParameter("artist_id")
        object MusicbrainzArtistID : RequestParameter("artist_mbid")
        object SortByReleaseData : RequestParameter("s_release_date")

        object GroupByAlbumName : RequestParameter("g_album_name")
    }

    override suspend fun handleErrors(response: HttpResponse) {
        println("RequestFailed with status code of ${response.responseStatusCode()}")
    }
    override suspend fun HttpResponse.responseMessage(): JsonElement? = json.decodeFromString<JsonObject>(bodyAsText())["message"]
    suspend fun musicGenresInCatalogue(): List<MusixGenre>?= performDefaultAction(ApiEndpoint.MusicGenresGet){
        null//MusixGenre.ktorDeserializer.deserializeList(it)
    }

    suspend fun trackLyricsMood(commonTrackID:Int): Pair<List<MusixMood>?, Map<MusixMood.RawData, Double>>? = performDefaultAction(ApiEndpoint.TrackLyricsMoodGet, parameters = mapOf(ApiRequestParameters.CommonTrackId to commonTrackID)){
        null//Pair(MusixMood.ktorDeserializer.deserializeList(it),MusixMood.extractRawData(it))
    }
    suspend fun trackLyricsMood(identifier:String): Pair<List<MusixMood>?, Map<MusixMood.RawData, Double>>? = performDefaultAction(ApiEndpoint.TrackLyricsMoodGet, parameters = mapOf(ApiRequestParameters.TrackISRC to identifier)){
        null//Pair(MusixMood.ktorDeserializer.deserializeList(it),MusixMood.extractRawData(it))
    }

    suspend fun trackSnippet(trackID:Int): MusixSnippet? = performDefaultAction(ApiEndpoint.TrackSnippetGet, parameters = mapOf(ApiRequestParameters.TrackId to trackID)){
        null//MusixSnippet.ktorDeserializer.deserialize(it)
    }

    suspend fun trackLyricsUsingTrackID(trackID: Int) : MusixLyrics? = performDefaultAction(ApiEndpoint.TrackLyricsGet, parameters = mapOf(ApiRequestParameters.TrackId to trackID)){
      null//MusixLyrics.ktorDeserializer.deserialize(it)
    }
    suspend fun trackLyricsUsingCommonTrackID(commonTrackID: Int) : MusixLyrics? = performDefaultAction(ApiEndpoint.TrackLyricsGet, parameters = mapOf(ApiRequestParameters.CommonTrackId to commonTrackID)){
       null// MusixLyrics.ktorDeserializer.deserialize(it)
    }

    //TODO catch error and return it
    suspend fun postTrackLyrics(commonTrackID: Int,lyrics:String): Unit? = performDefaultAction(ApiEndpoint.TrackLyricsPost, parameters = mapOf(ApiRequestParameters.CommonTrackId to commonTrackID,ApiRequestParameters.LyricsBody to lyrics)){}
    suspend fun postTrackLyrics(identifier: String,lyrics:String): Unit? = performDefaultAction(ApiEndpoint.TrackLyricsPost, parameters = mapOf(ApiRequestParameters.TrackISRC to identifier,ApiRequestParameters.LyricsBody to lyrics)){}

    suspend fun trackSubtitle(commonTrackID: Int, subtitleFormat: SubtitleFormat = SubtitleFormat.LRC, filterSubtitleLength:Int? = null, filterMaxDeviationLength:Int? = null): MusixSubtitle? = performDefaultAction(ApiEndpoint.TrackSubtitleGet,
        parameters = mapOf(
            ApiRequestParameters.CommonTrackId to commonTrackID,
            ApiRequestParameters.SubtitleFormat to subtitleFormat.asString,
            ApiRequestParameters.FilterSubtitleLength to filterSubtitleLength,
            ApiRequestParameters.FilterLengthMaxDeviation to filterMaxDeviationLength
        )
    ){ null/*MusixSubtitle.ktorDeserializer.deserialize(it)*/ }

    suspend fun topTracksOfCountry(chartName: ChartName = ChartName.Top, country:String? = null, filterHasLyrics:Boolean? = null, page:Int? = null, pageSize:Int? = null) : List<MusixTrack>? = performDefaultAction(ApiEndpoint.ChartTracksGet,
        parameters = mapOf(
            ApiRequestParameters.ChartName to chartName.asString,
            ApiRequestParameters.Country to country,
            ApiRequestParameters.FilterHasLyrics to filterHasLyrics,
            ApiRequestParameters.Page to page,
            ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"Page Size")
        }
    ){
        null
       // MusixTrack.ktorDeserializer.deserializeList(it)
    }

    suspend fun track(commonTrackID: Int) : MusixTrack? = performDefaultAction(ApiEndpoint.TrackGet, parameters = mapOf(ApiRequestParameters.CommonTrackId to commonTrackID)){
        null
    // MusixTrack.ktorDeserializer.deserialize(it)
    }

    suspend fun track(identifier: String) : MusixTrack? = performDefaultAction(ApiEndpoint.TrackGet, parameters = mapOf(ApiRequestParameters.TrackISRC to identifier)){
       null
       // MusixTrack.ktorDeserializer.deserialize(it)
    }

    suspend fun searchTrack(
        queryTrack:String? = null,
        queryArtist:String? = null,
        queryLyrics:String? = null,
        queryTrackArtist:String? = null,
        queryWriter:String? = null,
        queryWordInAlle:String? = null,
        filterArtistID : Int? = null,
        filterMusicGenreID : Int? = null,
        filterLyricsLanguage:String? = null,
        filterHasLyrics: Boolean? = null,
        filterTracksWithMinReleaseData:String? = null,
        filterTracksWithMaxReleaseData:String? = null,
        sortByArtistRating : PopularityIndex? = null,
        sortByTrackRating : PopularityIndex? = null,
        similarityThreshold : Float? = null,
        page : Int? = null,
        pageSize : Int? = null
    ): List<MusixTrack>? = performDefaultAction(ApiEndpoint.TrackSearch,
        parameters = mapOf(
            ApiRequestParameters.QueryTrack to queryTrack,
            ApiRequestParameters.QueryArtist to queryArtist,
            ApiRequestParameters.QueryLyrics to queryLyrics,
            ApiRequestParameters.QueryTrackArtist to queryTrackArtist,
            ApiRequestParameters.QueryWriter to queryWriter,
            ApiRequestParameters.QueryAlle to queryWordInAlle,
            ApiRequestParameters.FilterArtistID to filterArtistID,
            ApiRequestParameters.FilterMusicGenreID to filterMusicGenreID,
            ApiRequestParameters.FilterLyricsLanguage to filterLyricsLanguage,
            ApiRequestParameters.FilterHasLyrics to filterHasLyrics,
            ApiRequestParameters.FilterTrackReleaseDataMax to filterTracksWithMaxReleaseData,
            ApiRequestParameters.FilterTrackReleaseDataMin to filterTracksWithMinReleaseData,
            ApiRequestParameters.SortByArtistRating to sortByArtistRating,
            ApiRequestParameters.SortByTrackRating to sortByTrackRating,
            ApiRequestParameters.QuorumFactor to similarityThreshold,
            ApiRequestParameters.Page to page,
            ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"Page Size")
            similarityThreshold?.hasToBeInRange(0f,0.9f,"similarityThreshold")
        }
    ){
        null
        //MusixTrack.ktorDeserializer.deserializeList(it)
    }

    suspend fun matchTrack(queryTrack:String? = null, queryArtist:String? = null, queryAlbum:String? = null) : MusixTrack? = performDefaultAction(ApiEndpoint.MatcherTrackGet, parameters = mapOf(ApiRequestParameters.QueryTrack to queryTrack,ApiRequestParameters.QueryArtist to queryArtist,ApiRequestParameters.QueryAlbum to queryAlbum)){
        null
        // MusixTrack.ktorDeserializer.deserialize(it)
    }

    suspend fun tracksOfAlbumUsingAlbumID(
        albumID:Int,
        filterHasLyrics: Boolean? = null,
        page : Int? = null,
        pageSize : Int? = null
    ) : List<MusixTrack>? = performDefaultAction(ApiEndpoint.AlbumTrackGet,

        parameters = mapOf(
        ApiRequestParameters.AlbumID to albumID,
        ApiRequestParameters.FilterHasLyrics to filterHasLyrics,
        ApiRequestParameters.Page to page,
        ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"Page Size")
        }
    ){
        null
      //  MusixTrack.ktorDeserializer.deserializeList(it)
    }

    suspend fun tracksOfAlbumUsingMusicbrainzAlbumID(
        musicbrainzAlbumID :Int,
        filterHasLyrics: Boolean? = null,
        page : Int? = null,
        pageSize : Int? = null
    ) : List<MusixTrack>? = performDefaultAction(ApiEndpoint.AlbumTrackGet,
        parameters = mapOf(
            ApiRequestParameters.MusicbrainzAlbumID to musicbrainzAlbumID,
            ApiRequestParameters.FilterHasLyrics to filterHasLyrics,
            ApiRequestParameters.Page to page,
            ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"Page Size")
        }
    ){
        null
     //   MusixTrack.ktorDeserializer.deserializeList(it)
    }

    suspend fun album(albumID:Int): MusixAlbum? = performDefaultAction(ApiEndpoint.AlbumGet, parameters = mapOf(ApiRequestParameters.AlbumID to albumID)){
        null
    //   MusixAlbum.ktorDeserializer.deserialize(it)
    }

    suspend fun artistUsingArtistID(artistID:Int) : MusixArtist? = performDefaultAction(ApiEndpoint.ArtistGet, parameters = mapOf(ApiRequestParameters.ArtistID to artistID)){
        null
    // MusixArtist.ktorDeserializer.deserialize(it)
    }

    suspend fun artistUsingMusicbrainzArtistID(musicbrainzArtistID: Int) : MusixArtist? = performDefaultAction(ApiEndpoint.ArtistGet, parameters = mapOf(ApiRequestParameters.MusicbrainzArtistID to musicbrainzArtistID)){
        null
    //MusixArtist.ktorDeserializer.deserialize(it)
    }

    suspend fun searchArtist(queryArtist: String? = null,filterArtistID: Int? = null,filterMusicbrainzArtistID:Int? = null,page: Int? = null,pageSize: Int? = null) : List<MusixArtist>? = performDefaultAction(
        ApiEndpoint.ArtistSearch,
        parameters = mapOf(
            ApiRequestParameters.QueryArtist to queryArtist,
            ApiRequestParameters.FilterArtistID to filterArtistID,
            ApiRequestParameters.FilterMusicBrainzArtistID to filterMusicbrainzArtistID,
            ApiRequestParameters.Page to page,
            ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"pageSize")
        }
    ){
        null
        //MusixArtist.ktorDeserializer.deserializeList(it)
    }

    suspend fun albumsOfArtistUsingArtistID(
        artistID: Int,
        sortByReleaseData : PopularityIndex? = null,
        groupByAlbumName : Boolean? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ) : List<MusixAlbum>? = performDefaultAction(
        ApiEndpoint.ArtistAlbumsGet,
        parameters = mapOf(
            ApiRequestParameters.ArtistID to artistID,
            ApiRequestParameters.SortByReleaseData to sortByReleaseData,
            ApiRequestParameters.GroupByAlbumName to groupByAlbumName,
            ApiRequestParameters.Page to page,
            ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"pageSize")
        }
    ){
        null
    //    MusixAlbum.ktorDeserializer.deserializeList(it)
    }

    suspend fun albumsOfArtistUsingMusicBrainzArtistID(
        musicbrainzArtistID: Int,
        sortByReleaseData : PopularityIndex? = null,
        groupByAlbumName : Boolean? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ) : List<MusixAlbum>? = performDefaultAction(
        ApiEndpoint.ArtistAlbumsGet,
        parameters = mapOf(
            ApiRequestParameters.MusicbrainzArtistID to musicbrainzArtistID,
            ApiRequestParameters.SortByReleaseData to sortByReleaseData,
            ApiRequestParameters.GroupByAlbumName to groupByAlbumName,
            ApiRequestParameters.Page to page,
            ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"pageSize")
        }
    ){
        null
      //  MusixAlbum.ktorDeserializer.deserializeList(it)
    }

    suspend fun relatedArtistsUsingArtistID(
        artistID: Int,
        sortByReleaseData : PopularityIndex? = null,
        groupByAlbumName : Boolean? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ) : List<MusixArtist>? = performDefaultAction(ApiEndpoint.ArtistRelatedGet,
        parameters = mapOf(
            ApiRequestParameters.ArtistID to artistID,
            ApiRequestParameters.SortByReleaseData to sortByReleaseData,
            ApiRequestParameters.GroupByAlbumName to groupByAlbumName,
            ApiRequestParameters.Page to page,
            ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"pageSize")
        }
    ){
        null
      //  MusixArtist.ktorDeserializer.deserializeList(it)
    }
    suspend fun relatedArtistsUsingMusicBrainzArtistID(
        musicbrainzArtistID: Int,
        sortByReleaseData : PopularityIndex? = null,
        groupByAlbumName : Boolean? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ) : List<MusixArtist>? = performDefaultAction(ApiEndpoint.ArtistRelatedGet,
        parameters = mapOf(
            ApiRequestParameters.MusicbrainzArtistID to musicbrainzArtistID,
            ApiRequestParameters.SortByReleaseData to sortByReleaseData,
            ApiRequestParameters.GroupByAlbumName to groupByAlbumName,
            ApiRequestParameters.Page to page,
            ApiRequestParameters.PageSize to pageSize
        ),
        parameterCheck = {
            pageSize?.hasToBeInRange(1,100,"pageSize")
        }
    ){
        null
        //MusixArtist.ktorDeserializer.deserializeList(it)
    }
}