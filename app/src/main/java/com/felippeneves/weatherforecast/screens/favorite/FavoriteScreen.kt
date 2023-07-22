package com.felippeneves.weatherforecast.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felippeneves.weatherforecast.R
import com.felippeneves.weatherforecast.model.Favorite
import com.felippeneves.weatherforecast.navigation.WeatherScreens
import com.felippeneves.weatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(id = R.string.favorite_cities),
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController,
            onIconClicked = {
                navController.popBackStack()
            }
        )
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                val list = favoriteViewModel.favoritesList.collectAsState().value

                LazyColumn {
                    items(items = list) { item ->
                        CityRow(item, navController = navController, favoriteViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.popBackStack()
                navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0XFFB2DFDB),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = favorite.city,
                modifier = Modifier.padding(start = 8.dp)
            )

            Surface(
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = favorite.country,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(4.dp),
                )
            }

            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "delete_icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        favoriteViewModel.deleteFavorite(favorite)
                    },
                tint = Color.Red.copy(alpha = 0.3f)
            )
        }
    }

}
