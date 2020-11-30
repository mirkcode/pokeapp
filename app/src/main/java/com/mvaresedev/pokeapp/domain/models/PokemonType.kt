package com.mvaresedev.pokeapp.domain.models

import androidx.annotation.ColorRes
import com.mvaresedev.pokeapp.R

data class PokemonType(
        val name: String,
        @ColorRes val color: Int
)

enum class PokemonTypeColor(@ColorRes val colorInt: Int) {
    NORMAL(R.color.type_normal),
    FIRE(R.color.type_fire),
    FIGHTING(R.color.type_fighting),
    WATER(R.color.type_water),
    FLYING(R.color.type_flying),
    GRASS(R.color.type_grass),
    POISON(R.color.type_poison),
    ELECTRIC(R.color.type_electric),
    GROUND(R.color.type_ground),
    PSYCHIC(R.color.type_psychic),
    ROCK(R.color.type_rock),
    ICE(R.color.type_ice),
    BUG(R.color.type_bug),
    DRAGON(R.color.type_dragon),
    GHOST(R.color.type_ghost),
    DARK(R.color.type_dark),
    STEEL(R.color.type_steel),
    FAIRY(R.color.type_fairy),
    UNKNOWN(R.color.type_unknown)
}