package com.whereisdarran.well.feature.detail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
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
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.whereisdarran.well.data.Result
import com.whereisdarran.well.feature.detail.viewmodel.PokemonDetailViewModel
import com.whereisdarran.well.model.Pokemon

@Composable
fun PokemonDetail(pokemonId: Int, setTitle: (String) -> Unit) {
    val pokemonDetailViewModel: PokemonDetailViewModel = viewModel()
    val viewState by pokemonDetailViewModel.getPokemon(pokemonId).collectAsState()
    when (viewState) {
        is Result.Loading -> {
            CircularProgressIndicator()
        }
        is Result.Success -> {
            val pokemon = (viewState as Result.Success<Pokemon>).data
            setTitle.invoke(pokemon.name)
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)) {
                val (
                    pokemonImage, pokemonDescription
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
                        .constrainAs(pokemonImage) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        },
                )

                Text(
                    text = pokemon.description,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.constrainAs(pokemonDescription) {
                        top.linkTo(pokemonImage.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )
            }
        }
        is Result.Failure -> {
            Text(text = (viewState as Result.Failure).message ?: "")
        }
    }
}