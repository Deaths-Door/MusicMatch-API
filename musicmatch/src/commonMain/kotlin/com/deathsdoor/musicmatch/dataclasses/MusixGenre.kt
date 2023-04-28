package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.ktor_request_utils.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

@Serializable
data class MusixGenre(
    @SerialName("music_genre_id")
    val id: Int,
    @SerialName("music_genre_parent_id")
    val parentId: Int,
    @SerialName("music_genre_name")
    val name: String,
    @SerialName("music_genre_name_extended")
    val nameExtended: String,
    @SerialName("music_genre_vanity")
    val vanity: String?
){
    internal companion object {
        val ktorDeserializer by lazy {
            object : KtorDeserializer<MusixGenre> {
                override fun deserialize(jsonElement: JsonElement?): MusixGenre? = jsonElement?.jsonObject?.get("music_genre")?.let { RequestHandlerWrapper.json.decodeFromJsonElement(it) }
                override fun deserializeList(jsonElement: JsonElement?): List<MusixGenre>? = jsonElement?.jsonObject?.get("music_genre_list")?.jsonArray?.mapNotNull { deserialize(it.jsonObject) }
            }
        }
    }
    internal object GenresListSerializer : KSerializer<List<MusixGenre>>{
        override fun deserialize(decoder: Decoder): List<MusixGenre> = ktorDeserializer.deserializeList(decoder.asJsonElement) ?: emptyList()
        @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
        override val descriptor: SerialDescriptor = buildSerialDescriptor("GenresListSerializer", SerialKind.ENUM) {
            element<List<MusixGenre>>("music_genre_list")
        }
        override fun serialize(encoder: Encoder, value: List<MusixGenre>) = encoder.encodeValue(value)
    }
}