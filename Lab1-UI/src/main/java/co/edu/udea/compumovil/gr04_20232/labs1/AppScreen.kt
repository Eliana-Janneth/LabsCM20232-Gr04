package co.edu.udea.compumovil.gr04_20232.labs1

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr04_20232.labs1.ui.ContactDataScreen
import co.edu.udea.compumovil.gr04_20232.labs1.ui.PersonalDataScreen

enum class AppScreen(@StringRes val title: Int) {
    ContactData(title = R.string.contact_data_title),
    PersonalData(title = R.string.personal_data_title)
}

@Composable
fun ContactApp(
    navController: NavHostController = rememberNavController()
) {
    val infoViewModel : InfoViewModel = viewModel()
    NavHost(
        navController, startDestination = "personalData", modifier = Modifier.padding(
            PaddingValues(
                start = 0.dp,
                top = 25.dp,
                end = 0.dp,
                bottom = 0.dp
            )
        )
    ) {
        composable("personalData") {
            PersonalDataScreen(onNextButton = {
                navController.navigate("contactData")
            })
        }
        composable("contactData") {
            ContactDataScreen(viewModel = infoViewModel)
        }
    }
}

