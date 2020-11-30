package com.mvaresedev.pokeapp.di

import com.mvaresedev.pokeapp.domain.usecases.GetDetailsUseCase
import com.mvaresedev.pokeapp.domain.usecases.GetPokemonUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetPokemonUseCase(get()) }
    factory { GetDetailsUseCase(get()) }
}