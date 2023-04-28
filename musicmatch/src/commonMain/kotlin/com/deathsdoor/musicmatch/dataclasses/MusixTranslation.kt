package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.KtorDeserializer
import com.deathsdoor.ktor_request_utils.encodeValue
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement

@Serializable
data class MusixTranslation(
    @SerialName("language") val sprache: String,
    @SerialName("translation") val translation: String
){
    internal companion object {
        //TODO finish deserialization
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixTranslation>{
                override fun deserialize(jsonElement: JsonElement?): MusixTranslation? = null
                override fun deserializeList(jsonElement: JsonElement?): List<MusixTranslation>? = null
            }
        }
    }

    internal object TranslationListSerializerTrack : KSerializer<List<MusixTranslation>>{
        override fun deserialize(decoder: Decoder): List<MusixTranslation> = listOf()//ktorDeserializer.deserializeList(decoder.asJsonElement) ?: emptyList()
        @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
        override val descriptor: SerialDescriptor = buildSerialDescriptor("TranslationListSerializerTrack", SerialKind.CONTEXTUAL) {
            element<List<MusixTranslation>>("track_name_translation_list")
        }
        override fun serialize(encoder: Encoder, value: List<MusixTranslation>) = encoder.encodeValue(value)
    }
}
