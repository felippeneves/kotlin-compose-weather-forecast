package com.felippeneves.weatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felippeneves.weatherforecast.R
import com.felippeneves.weatherforecast.components.EmptyComponent
import com.felippeneves.weatherforecast.model.Favorite
import com.felippeneves.weatherforecast.navigation.WeatherScreens
import com.felippeneves.weatherforecast.screens.favorite.FavoriteViewModel
import com.felippeneves.weatherforecast.utils.DropDownMenuInf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onIconClicked: () -> Unit = {}
) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(
            showDialog = showDialog,
            navController = navController
        )
    }

    val context = LocalContext.current

    val showIt = remember {
        mutableStateOf(false)
    }

    Surface(shadowElevation = 5.dp) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
            actions = {
                if (isMainScreen) {
                    IconButton(onClick = {
                        onAddActionClicked.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search icon"
                        )
                    }
                    IconButton(onClick = {
                        showDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More icon"
                        )
                    }
                } else EmptyComponent()
            },
            navigationIcon = {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            onIconClicked.invoke()
                        }
                    )
                }

                if (isMainScreen) {

                    val isAlreadyFavList =
                        favoriteViewModel.favoritesList.collectAsState().value.filter { item ->
                            (item.city == title.split(",")[0])
                        }

                    if (isAlreadyFavList.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite icon",
                            modifier = Modifier
                                .scale(0.9f)
                                .clickable {
                                    val dataList = title.split(",")
                                    favoriteViewModel
                                        .insertFavorite(
                                            Favorite(
                                                city = dataList[0], // city name
                                                country = dataList[1] // country code
                                            )
                                        )
                                        .run {
                                            showIt.value = true
                                        }
                                },
                            tint = Color.Red.copy(alpha = 0.6f)
                        )
                    } else {
                        showIt.value = false
                        EmptyComponent()
                    }

                    ShowToast(context = context, showIt)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
        )
    }
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(
            context, stringResource(id = R.string.added_to_favorites),
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf(
        DropDownMenuInf(stringResource(id = R.string.about), Icons.Default.Info, WeatherScreens.AboutScreen.name),
        DropDownMenuInf(
            "Favorites",
            Icons.Default.FavoriteBorder,
            WeatherScreens.FavoriteScreen.name
        ),
        DropDownMenuInf(stringResource(id = R.string.settings), Icons.Default.Settings, WeatherScreens.SettingsScreen.name)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = item.icon, contentDescription = null,
                                tint = Color.LightGray
                            )
                            Text(
                                text = item.title,
                                modifier = Modifier
                                    .padding(start = 8.dp),
                                fontWeight = FontWeight.W300,
                            )
                        }
                    },
                    onClick = {
                        navController.navigate(item.routeScreen)
                        expanded = false
                        showDialog.value = false
                    })

            }
        }

    }
}
