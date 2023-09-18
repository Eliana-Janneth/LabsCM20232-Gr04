package co.edu.udea.compumovil.gr04_20232.labs1

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            appContact()
        }
    }
}

data class CountryData(
    val country_name: String,
    val country_short_name: String,
    val country_phone_code: Int,
)

data class DepartmentData(
    val department_name: String,
    val departmen_short_name: String,
    val departmen_phone_code: Int,
)

interface CountryApi {
    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InVzZXJfZW1haWwiOiJqdWFuZmxvcGVyYW1AZ21haWwuY29tIiwiYXBpX3Rva2VuIjoiU211Y0tVQTVZNk1fd3FJU2FKOThDN1dkcm8xZjd6N0VuQzcwaUVULVR4dEpKWTlZR2lvTTUzQWpnQlJtWWlsTGxFSSJ9LCJleHAiOjE2OTUxMzYzMTB9.TPgg5FDtLRpSjnUTYFdjX_u3_pYJM_zR_hC36v6M7kw",
        "Accept: application/json"
    )
    @GET("countries")
    suspend fun getAllCountries(): List<CountryData>

}


@Composable
fun appContact() {
    val focusRequesterPhone = remember { FocusRequester() }
    val focusRequesterEmail = remember { FocusRequester() }
    val focusRequesterCountry = remember { FocusRequester() }
    val focusRequesterDepartment = remember { FocusRequester() }
    val focusRequesterCity = remember { FocusRequester() }
    val focusRequesterAddress = remember { FocusRequester() }
    BoxWithConstraints {
        if (maxWidth < 600.dp) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text(
                    text = stringResource(id = R.string.contact_data_title),
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText(stringResource(id = R.string.phone), R.drawable.round_local_phone_24)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText(stringResource(id = R.string.email), R.drawable.round_email_24)

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter= painterResource(id = R.drawable.round_outlined_flag_24),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                    listCountryDropdown()
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter= painterResource(id = R.drawable.round_place_24),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    listCountryDropdown()
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter= painterResource(id = R.drawable.round_location_city_24),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    listCountryDropdown()
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText(stringResource(id = R.string.address), R.drawable.round_share_location_24)

                }
            }
        }
    }
}




object CountryApiService {
    private const val BASE_URL = "https://www.universal-tutorial.com/api/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val countryApi: CountryApi = retrofit.create(CountryApi::class.java)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listCountryDropdown() {
    var selectedCountry by remember { mutableStateOf<CountryData?>(null) }
    var selectedCountrys by remember { mutableStateOf("") }
    var countries by remember { mutableStateOf<List<CountryData>>(emptyList()) }
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                countries = CountryApiService.countryApi.getAllCountries()
            } catch (e: Exception) {
                Log.e("API Error", e.message ?: "Unknown error")
            }
        }
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        }
    )
    {
        TextField(
            value = selectedCountrys,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = stringResource(id = R.string.country))
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            Log.d("API", "Lista: " + countries.toString())
            countries.forEach { country ->
                Log.d("API", country.country_name)
                DropdownMenuItem(
                    text = {
                        Text(country.country_name)
                    },
                    onClick = {
                        selectedCountrys = country.country_name
                        isExpanded = false
                    }
                )
            }

            BasicTextField(
                value = TextFieldValue(selectedCountry?.country_name ?: "Select a country"),
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                modifier = Modifier.clickable { isExpanded = true }
            )
        }
    }
}
