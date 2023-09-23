package co.edu.udea.compumovil.gr04_20232.labs1

import android.util.Log
import androidx.lifecycle.ViewModel
import co.edu.udea.compumovil.gr04_20232.labs1.ui.InfoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InfoViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InfoUiState())
    val uiState: StateFlow<InfoUiState> = _uiState.asStateFlow()

    fun setFirstName(firstName: String){
        _uiState.update { currentState ->
            currentState.copy(
                firstName = firstName,
            )
        }
    }

    fun setLastName(lastName: String){
        _uiState.update { currentState ->
            currentState.copy(
                lastName = lastName,
            )
        }
    }

    fun setEmail(email: String){
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
            )
        }
    }

    fun setAddress(address: String){
        _uiState.update { currentState ->
            currentState.copy(
                address = address,
            )
        }
    }

    fun setPhone(phone: String){
        _uiState.update { currentState ->
            currentState.copy(
                phone = phone,
            )
        }
    }

    fun setGender(gender: String){
        _uiState.update { currentState ->
            currentState.copy(
                gender = gender,
            )
        }
    }

    fun setBirthdate(birthdate: String){
        _uiState.update { currentState ->
            currentState.copy(
                birthdate = birthdate,
            )
        }
    }

    fun setScholarship(scholarship: String){
        _uiState.update { currentState ->
            currentState.copy(
                scholarship = scholarship,
            )
        }
    }

    fun setCountry(country: String){
        Log.i("INF", country)
        _uiState.update { currentState ->
            currentState.copy(
                country = country,
            )
        }
    }

    fun setCity(city: String){
        Log.i("INF", city)
        _uiState.update { currentState ->
            currentState.copy(
                city = city,
            )
        }
    }
}