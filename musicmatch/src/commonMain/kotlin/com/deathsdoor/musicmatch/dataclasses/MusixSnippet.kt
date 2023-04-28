package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.IntToBooleanSerializer
import com.deathsdoor.ktor_request_utils.JsonDeserializer
import com.deathsdoor.ktor_request_utils.KtorDeserializer
import com.deathsdoor.ktor_request_utils.RequestHandlerWrapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlin.reflect.KClass

@Serializable
data class MusixSnippet(
    @SerialName("snippet_language") val language: String,
    @SerialName("snippet_id") val id: Int,
    @SerialName("restricted") @Serializable(with = IntToBooleanSerializer::class) val isRestricted: Boolean,
    @SerialName("instrumental") @Serializable(with = IntToBooleanSerializer::class) val isInstrumental: Boolean,
    @SerialName("snippet_body") val snippet_body: String,
    @SerialName("script_tracking_url") val script_tracking_url: String,
    @SerialName("pixel_tracking_url") val pixel_tracking_url: String,
    @SerialName("html_tracking_url") val html_tracking_url: String,
    @SerialName("updated_time") val updated_time: String
){
    internal companion object {
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixSnippet> {
                override fun deserialize(jsonElement: JsonElement?): MusixSnippet? = jsonElement?.jsonObject?.get("snippet")?.let { RequestHandlerWrapper.json.decodeFromJsonElement(it) }
            }
        }
    }
}