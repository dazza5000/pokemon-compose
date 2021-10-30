package com.whereisdarran.well.feature.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereisdarran.well.data.PokemonRepositoryImpl
import com.whereisdarran.well.data.Result
import com.whereisdarran.well.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {

    private val _pokemons: MutableStateFlow<Result<List<Pokemon>>> =
        MutableStateFlow(Result.Success(emptyList()))
    val pokemons: StateFlow<Result<List<Pokemon>>>
        get() = _pokemons

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _pokemons.value = PokemonRepositoryImpl.getRepository().getPokemons()
        }
    }
}


