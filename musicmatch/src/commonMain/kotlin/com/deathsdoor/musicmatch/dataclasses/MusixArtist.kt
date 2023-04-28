package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.IntToBooleanSerializer
import com.deathsdoor.ktor_request_utils.KtorDeserializer
import com.deathsdoor.ktor_request_utils.RequestHandlerWrapper
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class MusixArtist(
    @SerialName("artist_id") val id: Int,
    @SerialName("artist_name") val name: String,
    @SerialName("artist_rating") val rating: Int,
    @SerialName("artist_comment") val comment: String,
    @SerialName("artist_twitter_url") val twitterURL:String,
    @SerialName("artist_country") val country:String,

    @SerialName("artist_name_translation_list") @Serializable(with = MusixTranslation.TranslationListSerializerTrack::class) val ubersetztedNames:List<MusixTranslation>,

    @SerialName("artist_credits") val credits: List<MusixArtist> = emptyList(),
    @SerialName("artist_alias_list") @Serializable(with = ArtistAliasSerializer::class) val alternativname : List<String>,

    @SerialName("restricted") @Serializable(with = IntToBooleanSerializer::class) val isRestricted: Boolean,

    @SerialName("updated_time") val updatedTime: String,
    @SerialName("begin_date_year") val careerBeginYear: String,
    @SerialName("begin_date") val careerBeginDater: String,
    @SerialName("end_date_year") val careerEndYear: String,
    @SerialName("end_date") val careerEndDater: String,
){
    internal companion object {
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixArtist>{
                override fun deserialize(jsonElement: JsonElement?): MusixArtist? = jsonElement?.jsonObject?.get("artist")?.let { RequestHandlerWrapper.json.decodeFromJsonElement<MusixArtist>(it) }
            }
        }
    }

    internal object ArtistAliasSerializer: KSerializer<List<String>>{
        override fun deserialize(decoder: Decoder): List<String> = listOf()
        @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
        override val descriptor: SerialDescriptor =  buildSerialDescriptor("ArtistAliasSerializer", SerialKind.CONTEXTUAL) {
            element<List<String>>("artist_alias_list")
        }
        override fun serialize(encoder: Encoder, value: List<String>) {
            TODO("Not yet implemented")
        }
    }
}