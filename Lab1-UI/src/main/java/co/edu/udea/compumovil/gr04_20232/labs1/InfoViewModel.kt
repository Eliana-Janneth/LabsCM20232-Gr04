package co.edu.udea.compumovil.gr04_20232.labs1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class InfoViewModel: ViewModel() {
    var firstName: String by mutableStateOf("")
    var lastName: String by mutableStateOf("")
    var email: String by mutableStateOf("")
    var address: String by mutableStateOf("")
    var phone: String by mutableStateOf("")
    var gender: String by mutableStateOf("")
    var birthdate: String by mutableStateOf("")
    var scolarity: String by mutableStateOf("")
    var country: String by mutableStateOf("")
    var city: String by mutableStateOf("")
}