package com.whereisdarran.well.feature.list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Dimension.Companion.preferredWrapContent
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.whereisdarran.well.data.Result
import com.whereisdarran.well.feature.list.viewmodel.PokemonListViewModel
import com.whereisdarran.well.model.Pokemon


@Composable
fun PokemonList(
    navigateToPokemon: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pokemonListViewModel: PokemonListViewModel = viewModel()
    val viewState by pokemonListViewModel.pokemons.collectAsState()

    if (viewState is Result.Success) {
        Column(modifier) {
            PokemonLazyColumn(
                (viewState as Result.Success).data,
                navigateToPokemon = navigateToPokemon
            )
        }
    }
}

@Composable
private fun PokemonLazyColumn(
    pokemonList: List<Pokemon>,
    navigateToPokemon: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.Center
    ) {

        items(pokemonList, key = { it.id }) { item ->
            PokemonListItem(
                pokemon = item,
                onClick = navigateToPokemon,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .padding(vertical = 8.dp)
        .clickable { onClick(pokemon.id) }
    ) {
        val (
            image, pokemonName, pokemonDescription
        ) = createRefs()

        Image(
            painter = rememberImagePainter(
                data = pokemon.imageUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
        )

        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.constrainAs(pokemonName) {
                top.linkTo(parent.top)
                start.linkTo(image.end)
                width = preferredWrapContent
            }
        )

        Text(
            text = pokemon.description,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.constrainAs(pokemonDescription) {
                top.linkTo(pokemonName.bottom)
                start.linkTo(image.end)
                end.linkTo(parent.end)
                width = fillToConstraints
            }
        )
    }
}
