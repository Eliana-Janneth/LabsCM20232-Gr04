package co.edu.udea.compumovil.gr04_20232.labs1.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import co.edu.udea.compumovil.gr04_20232.labs1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class CountryData(
    val country: String,
    val cities: List<String>,
)

interface CountryApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("countries")
    suspend fun getAllCountries(): List<CountryData>
}

object CountryApiService {
    private const val BASE_URL = "https://apimocha.com/compumovil-lab-1/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val countryApi: CountryApi = retrofit.create(CountryApi::class.java)
}


@Composable
fun ContactDataScreen() {
    BoxWithConstraints {
        val colorBackground = Color(0xffbfdbff)
        val colorTittle = Color(0xff164583)
        val colorIcon = Color(0xff164583)
        if (maxWidth < 600.dp) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorBackground)
            ) {
                Text(
                    text = stringResource(id = R.string.contact_data_title),
                    fontSize = 28.sp,
                    color = colorTittle,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText(
                        stringResource(id = R.string.phone),
                        R.drawable.round_local_phone_24,
                        KeyboardType.Number,
                        KeyboardCapitalization.None
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText(
                        stringResource(id = R.string.email),
                        R.drawable.round_email_24,
                        KeyboardType.Email,
                        KeyboardCapitalization.None
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                listCountryDropdown()
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText(
                        stringResource(id = R.string.address),
                        R.drawable.round_share_location_24,
                        KeyboardType.Text,
                        KeyboardCapitalization.Sentences
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Spacer(Modifier.weight(1f))
                    val colorBack = Color(0xffa1cafe)
                    val colorText = Color(0xff043f8a)
                    Button(
                        onClick = {
                            // mDatePickerDialog.show()
                        },
                        modifier = Modifier.padding(30.dp),
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            top = 12.dp,
                            end = 20.dp,
                            bottom = 12.dp
                        ),
                        colors = ButtonDefaults.buttonColors(containerColor = colorBack)
                    ) {

                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            stringResource(id = R.string.next_button),
                            color = colorText,
                        )
                        Icon(
                            Icons.Filled.ArrowForward,
                            contentDescription = "ArrowForward",
                            tint = colorText
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listCountryDropdown() {
    var selectedCountry by remember { mutableStateOf<CountryData?>(null) }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var countries by remember { mutableStateOf<List<CountryData>>(emptyList()) }
    var cities by remember { mutableStateOf<List<String>>(emptyList()) }
    var isCountryExpanded by remember { mutableStateOf(false) }
    var isCityExpanded by remember { mutableStateOf(false) }
    val colorText = Color(0xff043f8a)
    val colorBack = Color(0xffa1cafe)
    val colorLabel = Color(0xff002a61)
    val colorIcon = Color(0xff164583)

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                countries = CountryApiService.countryApi.getAllCountries()
            } catch (e: Exception) {
                Log.e("API Error", e.message ?: "Unknown error")
            }
        }
    }

    Column {
        // Primer menú desplegable para países
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_outlined_flag_24),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = colorIcon

            )
            Spacer(modifier = Modifier.width(20.dp))

            ExposedDropdownMenuBox(
                expanded = isCountryExpanded,
                onExpandedChange = { newValue ->
                    isCountryExpanded = newValue
                }
            ) {
                TextField(
                    value = selectedCountry?.country ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCountryExpanded)
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.country))
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        textColor = colorText,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = colorText,
                        containerColor = colorBack,
                        focusedLabelColor = colorLabel,
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isCountryExpanded,
                    onDismissRequest = {
                        isCountryExpanded = false
                    }
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = {
                                Text(country.country)
                            },
                            onClick = {
                                selectedCountry = country
                                isCountryExpanded = false
                                // Actualiza las ciudades para el país seleccionado
                                cities = country.cities
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los menús desplegables

        // Segundo menú desplegable para ciudades
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_location_city_24),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = colorIcon
            )
            Spacer(modifier = Modifier.width(20.dp))
            ExposedDropdownMenuBox(
                expanded = isCityExpanded,
                onExpandedChange = { newValue ->
                    isCityExpanded = newValue
                }
            ) {
                TextField(
                    value = selectedCity ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCityExpanded)
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.city))
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        textColor = colorText,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = colorText,
                        containerColor = colorBack,
                        focusedLabelColor = colorLabel,
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isCityExpanded,
                    onDismissRequest = {
                        isCityExpanded = false
                    }
                ) {
                    cities.forEach { city ->
                        DropdownMenuItem(
                            text = {
                                Text(city)
                            },
                            onClick = {
                                selectedCity = city
                                isCityExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

