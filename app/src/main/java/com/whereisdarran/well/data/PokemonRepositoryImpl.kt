package com.whereisdarran.well.data

import com.whereisdarran.well.model.Pokemon
import com.whereisdarran.well.network.Network

class PokemonRepositoryImpl : PokemonRepository {
    private var pokemons: List<Pokemon>? = null

    override suspend fun getPokemons(): Result<List<Pokemon>> {
        if (pokemons == null) {
            val pokemonResponse = Network.pokemonService.getPokemon()

            if (pokemonResponse.isSuccessful) {
                pokemons = pokemonResponse.body()!!
            } else {
                return Result.Failure(
                    message =
                    pokemonResponse.errorBody()
                        ?.string()

                )
            }
        }
        return Result.Success(pokemons!!)
    }

    override suspend fun getPokemon(id: Int): Result<Pokemon> {
        if (pokemons == null) {
            getPokemons()
        }
        val pokemon = pokemons?.firstOrNull { it.id == id }

        return pokemon?.run {
            Result.Success(this)
        } ?: Result.Failure(message = "Unable to locate pokemon")
    }

    companion object {
        private var pokemonRepository: PokemonRepository? = null

        fun getRepository(): PokemonRepository {
            if (pokemonRepository == null) {
                pokemonRepository = PokemonRepositoryImpl()
            }
            return pokemonRepository!!
        }
    }
}

