package com.whereisdarran.well.feature.main

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whereisdarran.well.R
import com.whereisdarran.well.feature.detail.ui.PokemonDetail
import com.whereisdarran.well.feature.list.ui.PokemonList

@Composable
fun PokemonApp() {

    val navController = rememberNavController()

    val baseTitle = stringResource(id = R.string.app_name)
    val (title, setTitle) = remember { mutableStateOf(baseTitle) }

    Scaffold(topBar = { TopAppBar(title = { Text(text = title) }) }) {
        NavHost(navController = navController, startDestination = "pokemonList") {
            composable("pokemonList") {
                setTitle.invoke(baseTitle)
                PokemonList(navigateToPokemon = { pokemonId ->
                    navController.navigate("pokemonDetail/$pokemonId")
                })
            }
            composable(
                "pokemonDetail/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                val pokemonId = it.arguments?.getInt("id") ?: 0
                PokemonDetail(pokemonId, setTitle)
            }
        }
    }
}

