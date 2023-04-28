package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.IntToBooleanSerializer
import com.deathsdoor.ktor_request_utils.KtorDeserializer
import com.deathsdoor.ktor_request_utils.RequestHandlerWrapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class MusixAlbum(
    @SerialName("album_id") val id: Int,
    @SerialName("album_mbid") val musicBrainzIdentifier: String?,

    @SerialName("album_name") val name: String,
    @SerialName("album_rating") val rating: Int,
    @SerialName("album_release_date") val releaseDate: String,

    @SerialName("artist_id") val artistId: Int,
    @SerialName("artist_name") val artistName: String,
    @SerialName("album_pline") val albumPline: String,
    @SerialName("album_copyright") val albumCopyright: String,
    @SerialName("album_label") val albumLabel: String,

    @Serializable(with = MusixGenre.GenresListSerializer::class) @SerialName("primary_genres") val genres: List<MusixGenre>,

    @SerialName("restricted") @Serializable(with = IntToBooleanSerializer::class) val isRestricted:Boolean,
    @SerialName("external_ids") val externalIdentities : ExternalIdentities,

    @SerialName("updated_time") val updatedTime: String
){
    @Serializable
    data class ExternalIdentities(
        @SerialName("spotify") val spotify:List<String>,
        @SerialName("itunes") val itunes:List<String>,
        @SerialName("amazon_music") val amazonMusic:List<String>
    )

    internal companion object {
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixAlbum>{
                override fun deserialize(jsonElement: JsonElement?): MusixAlbum? = jsonElement?.jsonObject?.get("album")?.let { RequestHandlerWrapper.json.decodeFromJsonElement(it) }
            }
        }
    }
}
