package com.mvaresedev.pokeapp.data.db

import androidx.room.TypeConverter
import com.mvaresedev.pokeapp.domain.models.PokemonStat
import com.mvaresedev.pokeapp.domain.models.PokemonType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.koin.java.KoinJavaComponent.inject

class DbTypeConverter {

    private val moshi by inject(Moshi::class.java)

    @TypeConverter
    fun pokemonTypeListToString(types: List<PokemonType>): String {
        val listType = Types.newParameterizedType(List::class.java, PokemonType::class.java)
        val adapter: JsonAdapter<List<PokemonType>> = moshi.adapter(listType)
        return adapter.toJson(types)
    }

    @TypeConverter
    fun pokemonTypeStringToList(types: String): List<PokemonType> {
        val listType = Types.newParameterizedType(List::class.java, PokemonType::class.java)
        val adapter: JsonAdapter<List<PokemonType>> = moshi.adapter(listType)
        return adapter.fromJson(types)?: emptyList()
    }

    @TypeConverter
    fun pokemonStatListToString(stats: List<PokemonStat>): String {
        val listType = Types.newParameterizedType(List::class.java, PokemonStat::class.java)
        val adapter: JsonAdapter<List<PokemonStat>> = moshi.adapter(listType)
        return adapter.toJson(stats)
    }

    @TypeConverter
    fun pokemonStatStringToList(stats: String): List<PokemonStat> {
        val listType = Types.newParameterizedType(List::class.java, PokemonStat::class.java)
        val adapter: JsonAdapter<List<PokemonStat>> = moshi.adapter(listType)
        return adapter.fromJson(stats)?: emptyList()
    }

}