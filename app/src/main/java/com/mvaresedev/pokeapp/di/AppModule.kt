package com.mvaresedev.pokeapp.di

import com.mvaresedev.pokeapp.BuildConfig
import com.mvaresedev.pokeapp.domain.repo.PokeRepository
import com.mvaresedev.pokeapp.data.repo.PokeRepositoryImpl
import com.mvaresedev.pokeapp.data.db.PokemonDatabase
import com.mvaresedev.pokeapp.domain.mapper.DatabaseMapper
import com.mvaresedev.pokeapp.data.db.mapper.DatabaseMapperImpl
import com.mvaresedev.pokeapp.data.network.PokemonApi
import com.mvaresedev.pokeapp.domain.mapper.NetworkMapper
import com.mvaresedev.pokeapp.data.network.mapper.NetworkMapperImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://pokeapi.co/api/v2/"


val appModule = module {

    single {
        val client = OkHttpClient().newBuilder()

        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }

        client.build()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(PokemonApi::class.java)
    }

    single { PokemonDatabase.create(androidContext()) }

    single<DatabaseMapper> { DatabaseMapperImpl() }

    single<NetworkMapper> { NetworkMapperImpl() }

    single<PokeRepository> {
        PokeRepositoryImpl(
            get(),
            get(),
            get(),
            get()
        )
    }
}