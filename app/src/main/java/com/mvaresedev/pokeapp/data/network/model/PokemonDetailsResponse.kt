package com.mvaresedev.pokeapp.data.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailsResponse(
    @Json(name = "abilities")
    val abilities: List<Ability>,
    @Json(name = "height")
    val height: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "sprites")
    val sprites: Sprites,
    @Json(name = "stats")
    val stats: List<Stat>,
    @Json(name = "types")
    val types: List<Type>,
    @Json(name = "weight")
    val weight: Int
) {
    @JsonClass(generateAdapter = true)
    data class Ability(
        @Json(name = "ability")
        val ability: Ability,
    ) {
        @JsonClass(generateAdapter = true)
        data class Ability(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Sprites(
        @Json(name = "front_default")
        val frontDefault: String?,
        @Json(name = "other")
        val other: Other
    ) {
        @JsonClass(generateAdapter = true)
        data class Other(
                @Json(name = "official-artwork")
                val officialArtwork: OfficialArtwork
        ) {
            @JsonClass(generateAdapter = true)
            data class OfficialArtwork(
                    @Json(name = "front_default")
                    val frontDefault: String?
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class Stat(
        @Json(name = "base_stat")
        val baseStat: Int,
        @Json(name = "effort")
        val effort: Int,
        @Json(name = "stat")
        val stat: Stat
    ) {
        @JsonClass(generateAdapter = true)
        data class Stat(
            @Json(name = "name")
            val name: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Type(
        @Json(name = "type")
        val type: Type
    ) {
        @JsonClass(generateAdapter = true)
        data class Type(
            @Json(name = "name")
            val name: String
        )
    }
}