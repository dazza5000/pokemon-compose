package com.whereisdarran.well.data

import com.whereisdarran.well.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemons(): Result<List<Pokemon>>
    suspend fun getPokemon(id: Int): Result<Pokemon>
}

sealed class Result<out T> {
    data class Success<out R>(val data: R) : Result<R>()
    data class Failure(
        val message: String?,
        val throwable: Throwable? = null
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()
}