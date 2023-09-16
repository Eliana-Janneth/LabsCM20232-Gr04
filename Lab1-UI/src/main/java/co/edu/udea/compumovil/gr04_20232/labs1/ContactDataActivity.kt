package co.edu.udea.compumovil.gr04_20232.labs1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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

interface CountryApi {
    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7InVzZXJfZW1haWwiOiJqdWFuZmxvcGVyYW1AZ21haWwuY29tIiwiYXBpX3Rva2VuIjoiU211Y0tVQTVZNk1fd3FJU2FKOThDN1dkcm8xZjd6N0VuQzcwaUVULVR4dEpKWTlZR2lvTTUzQWpnQlJtWWlsTGxFSSJ9LCJleHAiOjE2OTQ5NzM1MjN9.OJLyFQyNDRnd1TVJMgKr4gkf4eayWJ6H6C9iuvkGAm4",
        "Accept: application/json"
    )
    @GET("countries")
    suspend fun getAllCountries(): List<CountryData>

}

@Composable
fun appContact() {
    BoxWithConstraints {
        if (maxWidth < 600.dp) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text(
                    text = "Información De Contacto",
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
                    inputText("Teléfono", R.string.personIcon)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText("Email", R.string.personIcon)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listCountryDropdown()
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText("Falta ciudades", R.string.personIcon)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    inputText("Dirección", R.string.personIcon)
                }




            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inputText(input: String, icono: Int) {
    Icon(
        Icons.Rounded.Person,
        contentDescription = stringResource(id = icono),
        modifier = Modifier.size(48.dp)

    )
    var inputName by remember { mutableStateOf("") }
    OutlinedTextField(
        value = inputName,
        onValueChange = { inputName = it },
        label = { Text(input) },
        maxLines = 1,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        ),
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        )
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
fun listCountryDropdown(){
    var selectedCountry by remember { mutableStateOf<CountryData?>(null) }
    var selectedCountrys by remember { mutableStateOf("") }
    var countries by remember { mutableStateOf<List<CountryData>>(emptyList()) }
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                countries = CountryApiService.countryApi.getAllCountries()
                Log.d("API Response","Prueba de log")
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
                    Text(text = "País")
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
                Log.d("API", "Lista: "+countries.toString())
                countries.forEach{
                    country ->
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





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropdown() {
    var selectedCountry by remember { mutableStateOf<CountryData?>(null) }
    //var countries by remember { mutableStateOf<List<CountryData>>(emptyList()) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val response = CountryApiService.countryApi.getAllCountries()
                Log.d("API Response","Prueba de log")
                Log.d("API Response",response.toString())
            } catch (e: Exception) {
                Log.e("API Error", e.message ?: "Unknown error")
            }
        }
    }
        var isExpanded by remember {
            mutableStateOf(false)
        }

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { newValue ->
                isExpanded = newValue
            }
        )
        {

            TextField(
                value = selectedCountry?.country_name ?: "",
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                placeholder = {},
                modifier = Modifier.fillMaxWidth(),
                )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false }
        ) {

                DropdownMenuItem(
                    text = {
                        Text(text = "Prueba")
                    },
                    onClick = {
                        //selectedCountry = country
                        isMenuExpanded = false
                    }
                )

        }
    }
