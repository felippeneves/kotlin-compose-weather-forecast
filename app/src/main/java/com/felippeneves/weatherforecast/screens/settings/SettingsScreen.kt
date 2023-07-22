package com.felippeneves.weatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felippeneves.weatherforecast.R
import com.felippeneves.weatherforecast.model.Settings
import com.felippeneves.weatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val unitToggleState = remember { mutableStateOf(false) }
    val measurementUnits = listOf(
        stringResource(id = R.string.imperial_f),
        stringResource(id = R.string.metric_c)
    )
    val defaultUnit = measurementUnits[0]
    val choiceState = remember { mutableStateOf(defaultUnit) }
    val choiceFromDb = settingsViewModel.settingsList.collectAsState()

    LaunchedEffect(choiceFromDb.value) {
        val unit = choiceFromDb.value[0].unit
        unitToggleState.value = unit ==  measurementUnits[1]
        choiceState.value = unit
    }

//    val choiceState = remember { mutableStateOf(measurementUnits[0]) }
//
//    LaunchedEffect(Unit) {
//        val choiceFromDb = settingsViewModel.settingsList.value[0].unit
//        unitToggleState.value = choiceFromDb ==  measurementUnits[1]
//        choiceState.value = choiceFromDb
//    }

    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(id = R.string.settings),
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
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.msg_change_units),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState.value,
                    onCheckedChange = {
                        unitToggleState.value = !it
                        choiceState.value =
                            if (unitToggleState.value) measurementUnits[1] else measurementUnits[0]
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(8.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = if (choiceState.value == stringResource(id = R.string.imperial_f)) stringResource(
                            id = R.string.fahrenheit_f
                        )
                        else stringResource(
                            id = R.string.celsius_c
                        )
                    )
                }

                Button(
                    onClick = {
                        settingsViewModel.deleteAllSettings()
                        settingsViewModel.insertSettings(Settings(unit = choiceState.value))
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(4.dp),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}