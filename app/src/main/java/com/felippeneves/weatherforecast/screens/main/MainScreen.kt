package com.felippeneves.weatherforecast.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felippeneves.weatherforecast.data.wrapper_class.DataOrException
import com.felippeneves.weatherforecast.model.Weather
import com.felippeneves.weatherforecast.navigation.WeatherScreens
import com.felippeneves.weatherforecast.screens.settings.SettingsViewModel
import com.felippeneves.weatherforecast.utils.Constants
import com.felippeneves.weatherforecast.utils.formatDate
import com.felippeneves.weatherforecast.utils.formatDecimals
import com.felippeneves.weatherforecast.widgets.DaysOfWeek
import com.felippeneves.weatherforecast.widgets.HumidityWindPressureRow
import com.felippeneves.weatherforecast.widgets.SunsetSunriseRow
import com.felippeneves.weatherforecast.widgets.WeatherAppBar
import com.felippeneves.weatherforecast.widgets.WeatherStateImage
import java.lang.Exception

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val curCity: String = if (city!!.isBlank()) Constants.DEFAULT_CITY else city
    val unitFromDb = settingsViewModel.settingsList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }

    var isImperial by remember {
        mutableStateOf(false)
    }

    if (unitFromDb.isNotEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(
                city = curCity,
                units = unit
            )
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(
                weather = weatherData.data!!,
                navController = navController,
                isImperial = isImperial
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    weather: Weather,
    navController: NavController,
    isImperial: Boolean
) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ", ${weather.city.country}",
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            MainContent(
                data = weather,
                isImperial = isImperial
            )
        }
    }
}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {

    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherItem.temp.day) + "°" + if (isImperial) "F" else "C",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(
            weather = weatherItem,
            isImperial = isImperial
        )
        Divider()
        SunsetSunriseRow(weather = weatherItem)
        Text(
            text = "This Week",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        DaysOfWeek(data = data, isImperial = isImperial)
    }
}