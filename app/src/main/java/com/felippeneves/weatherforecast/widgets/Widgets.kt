package com.felippeneves.weatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.felippeneves.weatherforecast.R
import com.felippeneves.weatherforecast.model.Weather
import com.felippeneves.weatherforecast.model.WeatherItem
import com.felippeneves.weatherforecast.utils.formatDateTime
import com.felippeneves.weatherforecast.utils.formatDecimals
import com.felippeneves.weatherforecast.utils.getDayOfWeek

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = stringResource(id = R.string.humidity_icon),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = stringResource(id = R.string.pressure_icon),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = stringResource(id = R.string.wind_icon),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDecimals(weather.speed)} " + if (isImperial) stringResource(id = R.string.mph)
                else stringResource(id = R.string.ms),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Icon image",
        modifier = Modifier.size(80.dp),
    )
}

@Composable
fun DaysOfWeek(data: Weather, isImperial: Boolean) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(size = 14.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(items = data.list) { item: WeatherItem ->
                WeatherDetailRow(weather = item, isImperial = isImperial)
            }
        }
    }
}

@Composable
fun WeatherDetailRow(weather: WeatherItem, isImperial: Boolean) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)), //Customize the shape
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = getDayOfWeek(weather.dt),
                modifier = Modifier.padding(start = 8.dp)
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(
                    text = weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue.copy(alpha = 0.7f),
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(formatDecimals(weather.temp.max) + "°" + if (isImperial) "F" else "C")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                        )
                    ) {
                        append(formatDecimals(weather.temp.min) + "°" + if (isImperial) "F" else "C")
                    }
                },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}