package com.mvaresedev.pokeapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvaresedev.pokeapp.data.db.dao.PokemonDao
import com.mvaresedev.pokeapp.data.db.dao.RemoteKeyDao
import com.mvaresedev.pokeapp.data.db.entities.PokemonEntity
import com.mvaresedev.pokeapp.data.db.entities.RemoteKeyEntity


@Database(entities = [PokemonEntity::class, RemoteKeyEntity::class], version = 1)
@TypeConverters(DbTypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDao
    abstract fun getRemoteKeyDao(): RemoteKeyDao

    companion object {

        private const val DB_NAME = "pokemon_db"

        fun create(context: Context): PokemonDatabase {
            return Room.databaseBuilder(
                context,
                PokemonDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}
