package com.deathsdoor.musicmatch

import com.deathsdoor.musicmatch.enums.ChartName
import com.deathsdoor.musicmatch.enums.PopularityIndex
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

//TODO make all tests and check it
class ApiTest {
    private val musixAbgleich = MusixAbgleich("96868e3713c6d6ced65dd79a1196771c")
    @Deprecated("", ReplaceWith("FUCK YOU")) private val dashes get() = "\n---------------------------------"

    @Test
    fun musicGenresInCatalogue() = runTest{
        assertTrue(musixAbgleich.musicGenresInCatalogue().isNullOrEmpty())
    }

    @Test
    fun trackSnippet() = runTest{
        assertTrue(musixAbgleich.trackSnippet(15953433) != null)
    }

    @Test
    fun testTrackLyricsUsingTrackID() = runTest {
        val lyrics = musixAbgleich.trackLyricsUsingTrackID(15953433)
        println(lyrics)
    }

    @Test
    fun testTrackLyricsUsingCommonTrackID() = runTest {
        val lyrics = musixAbgleich.trackLyricsUsingCommonTrackID(5920049)
        println(lyrics)
    }

    @Test
    fun testTopTracksOfCountry() = runTest {
        val tracks = musixAbgleich.topTracksOfCountry(
            chartName = ChartName.Top,
            country = "it",
            filterHasLyrics = true,
            page = 1,
            pageSize = 5
        )
        assertTrue(tracks?.isNotEmpty() == true)
    }

    @Test
    fun testTrack() = runTest {
        val track = musixAbgleich.track(5920049)
        assertNotNull(track)
        assertEquals("Lose Yourself", track.name)
    }

    @Test
    fun testSearchTrack() = runTest {
        val tracks = musixAbgleich.searchTrack(
            queryArtist = "Eminem",
            queryTrack = "Lose Yourself",
            pageSize = 3,
            page = 1,
            sortByTrackRating = PopularityIndex.Descending
        )
        assertTrue(tracks?.isNotEmpty() == true)
    }

    @Test
    fun testAlbum() = runTest {
        val album = musixAbgleich.album(14250417)
        assertNotNull(album)
        assertEquals("Recovery", album.name)
    }


    @Test
    fun artistUsingArtistID() = runTest {
        val x = musixAbgleich.artistUsingArtistID(118)
        println(x)
    }
    private suspend fun worksButIncomplete(){
        println("musixAbgleich.postTrackLyrics() = ${musixAbgleich.postTrackLyrics(5920049,"....")}$dashes")
        println("musixAbgleich.postTrackLyrics() = ${musixAbgleich.postTrackLyrics("....",".....")}$dashes")
    }
    private suspend fun cantCheckDueToPlan(){
        println("cantCheckDueToPlan has been triggered")
        println("musixAbgleich.trackLyricsMood() = ${musixAbgleich.trackLyricsMood("")}")
        println("musixAbgleich.trackLyricsMood() = ${musixAbgleich.trackLyricsMood(5920049)}")
        println("musixAbgleich.trackSubtitle() = ${musixAbgleich.trackSubtitle(5920049)}$dashes")
    }
}