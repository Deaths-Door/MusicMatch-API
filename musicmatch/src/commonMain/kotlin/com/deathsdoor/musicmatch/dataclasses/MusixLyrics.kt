package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlin.reflect.KClass

@Serializable
data class MusixLyrics(
    @SerialName("lyrics_id") val id:Int,
    @SerialName("restricted") @Serializable(with = IntToBooleanSerializer::class) val isRestricted:Boolean = false,
    @SerialName("instrumental") @Serializable(with = IntToBooleanSerializer::class) val isInstrumental:Boolean = false,
    @SerialName("explicit") @Serializable(with = IntToBooleanSerializer::class) val isExplicit:Boolean,
    @SerialName("lyrics_body") val lyrics:String,
    @SerialName("lyrics_language") val language:String = "US",
    @SerialName("script_tracking_url") val scriptTrackingURL:String = "",
    @SerialName("pixel_tracking_url") val pixelTrackingURL:String = "",
    @SerialName("lyrics_copyright") val copyRight:String,
    @SerialName("backlink_url") val backLinkURL:String = "",
    @SerialName("updated_time") val updatedTime:String,
){
    internal companion object {
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixLyrics> {
                override fun deserialize(jsonElement: JsonElement?): MusixLyrics = RequestHandlerWrapper.json.decodeFromJsonElement(jsonElement?.jsonObject!!["lyrics"]!!)
            }
        }
    }
}