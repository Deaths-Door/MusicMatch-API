package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.IntToBooleanSerializer
import com.deathsdoor.ktor_request_utils.KtorDeserializer
import com.deathsdoor.ktor_request_utils.RequestHandlerWrapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
data class MusixSubtitle(
    @SerialName("subtitle_id") val subtitleId: Int,
    @SerialName("restricted")  @Serializable(with = IntToBooleanSerializer::class) val isRestricted: Boolean,
    //TODO change type base on format used
    //TODO change from string into list
    @SerialName("subtitle_body") val subtitleBody: String,
    @SerialName("subtitle_language") val subtitleLanguage: String,
    @SerialName("script_tracking_url") val scriptTrackingUrl: String,
    @SerialName("pixel_tracking_url") val pixelTrackingUrl: String,
    @SerialName("html_tracking_url") val htmlTrackingUrl: String,
    @SerialName("lyrics_copyright") val lyricsCopyright: String
){
    internal companion object {
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixSubtitle> {
                override fun deserialize(jsonElement: JsonElement?): MusixSubtitle? = jsonElement?.let { RequestHandlerWrapper.json.decodeFromJsonElement(it) }
            }
        }
    }
}