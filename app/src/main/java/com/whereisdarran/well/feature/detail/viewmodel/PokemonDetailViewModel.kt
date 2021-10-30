package com.whereisdarran.well.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereisdarran.well.data.PokemonRepositoryImpl
import com.whereisdarran.well.data.Result
import com.whereisdarran.well.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {

    private val _pokemon: MutableStateFlow<Result<Pokemon>> =
        MutableStateFlow(Result.Loading)
    private val pokemon: StateFlow<Result<Pokemon>>
        get() = _pokemon

    fun getPokemon(id: Int): StateFlow<Result<Pokemon>> {
        viewModelScope.launch(Dispatchers.IO) {
            _pokemon.value = PokemonRepositoryImpl.getRepository().getPokemon(id)
        }
        return pokemon
    }
}


