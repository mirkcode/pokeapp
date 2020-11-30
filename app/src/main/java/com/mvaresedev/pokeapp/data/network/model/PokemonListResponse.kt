package com.mvaresedev.pokeapp.data.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListResponse(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: String?,
    @Json(name = "previous")
    val previous: String?,
    @Json(name = "results")
    val results: List<PokemonResult>
) {
    @JsonClass(generateAdapter = true)
    data class PokemonResult(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String
    )
}