package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.RequestHandlerWrapper
import com.deathsdoor.ktor_request_utils.hasToBeInRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.*

data class MusixMood(
    @SerialName("label") val name: String,
    @SerialName("value") val value: Float
)
{
    init {
        value.hasToBeInRange(0f,1f,"value")
    }

    enum class RawData{
        Valence,
        Arousal
    }

    internal companion object {
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixMood> {
                override fun deserialize(jsonElement: JsonElement?): MusixMood? = jsonElement?.let { RequestHandlerWrapper.json.decodeFromJsonElement(it) }
                override fun deserializeList(jsonElement: JsonElement?): List<MusixMood>? = jsonElement?.jsonArray?.let { RequestHandlerWrapper.json.decodeFromJsonElement(it) }
            }
        }

        val extractRawData : (JsonObject?) -> Map<RawData, Double> = {
            mapOf(
                RawData.Valence to it!!["raw_data"]!!.jsonObject["valence"]!!.jsonPrimitive.double,
                RawData.Arousal to it["raw_data"]!!.jsonObject["arousal"]!!.jsonPrimitive.double
            )
        }
    }
}