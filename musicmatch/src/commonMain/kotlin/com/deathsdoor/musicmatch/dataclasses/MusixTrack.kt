package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.IntToBooleanSerializer
import com.deathsdoor.ktor_request_utils.KtorDeserializer
import com.deathsdoor.ktor_request_utils.RequestHandlerWrapper
import com.deathsdoor.musicmatch.dataclasses.MusixTranslation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class MusixTrack(
    @SerialName("track_id") val id: Int,
    @SerialName("track_name") val name: String,
    @SerialName("track_rating") val rating: Int,
    @SerialName("num_favourite") val numberMalAddedToFavouriteByMusicMatchUsers: Int,
    @SerialName("commontrack_id") val commonTrackId: Int,
    @SerialName("instrumental") @Serializable(with = IntToBooleanSerializer::class) val isInstrumental: Boolean,
    @SerialName("explicit") @Serializable(with = IntToBooleanSerializer::class) val isExplicit: Boolean,
    @SerialName("has_lyrics") @Serializable(with = IntToBooleanSerializer::class) val hasLyrics: Boolean,
    @SerialName("has_subtitles") @Serializable(with = IntToBooleanSerializer::class) val hasSubtitles: Boolean,
    @SerialName("has_richsync") @Serializable(with = IntToBooleanSerializer::class) val hasRichsync: Boolean,
    @SerialName("album_id") val albumId: Int,
    @SerialName("album_name") val albumName: String,
    @SerialName("artist_id") val artistId: Int,
    @SerialName("artist_name") val artistName: String,
    @SerialName("track_share_url") val shareUrl: String,
    @SerialName("track_edit_url") val editUrl: String,
    @SerialName("restricted") @Serializable(with = IntToBooleanSerializer::class) val isRestricted: Boolean,
    @SerialName("updated_time") val updatedTime: String,
    @SerialName("primary_genres") @Serializable(with = MusixGenre.GenresListSerializer::class) val genres: List<MusixGenre>,

    //TODO finish with new class
    @SerialName("track_name_translation_list") @Serializable(with = MusixTranslation.TranslationListSerializerTrack::class) val translatedNames:List<MusixTranslation>
){
    internal companion object {
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixTrack>{
                override fun deserialize(jsonElement: JsonElement?): MusixTrack? = jsonElement?.jsonObject?.get("track")?.let { RequestHandlerWrapper.json.decodeFromJsonElement<MusixTrack>(it) }
                override fun deserializeList(jsonElement: JsonElement?): List<MusixTrack>? = jsonElement?.jsonObject?.get("track_list")?.jsonArray?.mapNotNull { deserialize(it) }
            }
        }
    }
}