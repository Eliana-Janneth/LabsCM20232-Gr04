@file:OptIn(ExperimentalMaterial3Api::class)

package co.edu.udea.compumovil.gr04_20232.labs1.ui

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr04_20232.labs1.InfoViewModel
import co.edu.udea.compumovil.gr04_20232.labs1.R

@Composable
fun PersonalDataScreen(onNextButton: (Int) -> Unit) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            personalDataHorizontalLayout(onNextButton)
        }
        else -> {
            personalDataVerticalLayout(onNextButton)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun personalDataHorizontalLayout(
    onNextButton: (Int) -> Unit,
    infoViewModel: InfoViewModel = viewModel()
) {
    val infoUiState by infoViewModel.uiState.collectAsState()
    val screenTitle = stringResource(id = R.string.personal_data_title)
    val firstname = stringResource(id = R.string.firstname)
    val lastName = stringResource(id = R.string.lastname)
    val gender = stringResource(id = R.string.gender)
    val birthdate = stringResource(id = R.string.birthdate)
    val scholarship = stringResource(id = R.string.scolarity)
    var validation by remember {
        mutableStateOf(false)
    }
    DisposableEffect(infoUiState.firstName, infoUiState.lastName, infoUiState.birthdate) {
        validation =
            !infoUiState.firstName.isNullOrEmpty() and !infoUiState.lastName.isNullOrEmpty() and !infoUiState.birthdate.isNullOrEmpty()
        onDispose { }
    }
    BoxWithConstraints {
        val colorBackground = Color(0xffbfdbff)
        val colorTittle = Color(0xff164583)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorBackground)
        ) {
            Text(
                text = stringResource(id = R.string.personal_data_title),
                fontSize = 28.sp,
                color = colorTittle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp, bottom = 1.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                inputText(
                    stringResource(id = R.string.firstname) + "*",
                    R.drawable.round_person_24,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.firstName,
                    onValueChange = { infoViewModel.setFirstName(it) }
                )
                Spacer(modifier = Modifier.width(10.dp))
                inputText(
                    stringResource(id = R.string.lastname) + "*",
                    R.drawable.round_person_add_24,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.lastName,
                    onValueChange = { infoViewModel.setLastName(it) }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,

                ) {
                radioGender()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                selectBirthday()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                selectStudy()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.required_fields),
                    fontSize = 11.sp,
                    color = colorTittle,
                    modifier = Modifier
                        .padding(top = 15.dp),
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                val colorBack = Color(0xffa1cafe)
                val colorText = Color(0xff043f8a)
                Button(
                    enabled = validation,
                    onClick = {
                        onNextButton(2)
                        Log.i(
                            "", "${screenTitle.uppercase()}\n" +
                                    "---------------------------------------\n" +
                                    "$firstname= ${infoUiState.firstName}\n" +
                                    "$lastName = ${infoUiState.lastName}\n" +
                                    if (!infoUiState.gender.isEmpty()) {
                                        "$gender = ${infoUiState.gender}\n"
                                    } else {
                                        ""
                                    }
                                    + "$birthdate = ${infoUiState.birthdate}\n" +
                                    if (!infoUiState.scholarship.isEmpty()) {
                                        "$scholarship = ${infoUiState.scholarship}\n"
                                    } else {
                                        ""
                                    }
                        )
                    },
                    modifier = Modifier.padding(end = 100.dp, bottom = 20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorBack)
                ) {
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        stringResource(R.string.next_button),
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

@Composable
fun personalDataVerticalLayout(
    onNextButton: (Int) -> Unit,
    infoViewModel: InfoViewModel = viewModel()
) {
    val infoUiState by infoViewModel.uiState.collectAsState()

    val screenTitle = stringResource(id = R.string.personal_data_title)
    val firstname = stringResource(id = R.string.firstname)
    val lastName = stringResource(id = R.string.lastname)
    val gender = stringResource(id = R.string.gender)
    val birthdate = stringResource(id = R.string.birthdate)
    val scholarship = stringResource(id = R.string.scolarity)
    var validation by remember {
        mutableStateOf(false)
    }
    DisposableEffect(infoUiState.firstName, infoUiState.lastName, infoUiState.birthdate) {
        validation =
            !infoUiState.firstName.isNullOrEmpty() and !infoUiState.lastName.isNullOrEmpty() and !infoUiState.birthdate.isNullOrEmpty()
        onDispose { }
    }

    BoxWithConstraints {
        val colorBackground = Color(0xffbfdbff)
        val colorTittle = Color(0xff164583)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorBackground)
        ) {
            Text(
                text = stringResource(id = R.string.personal_data_title),
                fontSize = 28.sp,
                color = colorTittle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                inputText(
                    stringResource(id = R.string.firstname) + "*",
                    R.drawable.round_person_24,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.firstName,
                    onValueChange = { infoViewModel.setFirstName(it) }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                inputText(
                    stringResource(id = R.string.lastname) + "*",
                    R.drawable.round_person_add_24,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    infoUiState.lastName,
                    onValueChange = { infoViewModel.setLastName(it) }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                radioGender()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                selectBirthday()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                selectStudy()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = stringResource(id = R.string.required_fields),
                    fontSize = 11.sp,
                    color = colorTittle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    textAlign = TextAlign.Center
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
                        onNextButton(2)
                        Log.i(
                            "", "${screenTitle.uppercase()}\n" +
                                    "---------------------------------------\n" +
                                    "$firstname = ${infoUiState.firstName}\n" +
                                    "$lastName = ${infoUiState.lastName}\n" +
                                    if (!infoUiState.gender.isEmpty()) {
                                        "$gender = ${infoUiState.gender}\n"
                                    } else {
                                        ""
                                    }
                                    + "$birthdate = ${infoUiState.birthdate}\n" +
                                    if (!infoUiState.scholarship.isEmpty()) {
                                        "$scholarship = ${infoUiState.scholarship}\n"
                                    } else {
                                        ""
                                    }
                        )
                    },
                    modifier = Modifier.padding(30.dp),
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    colors = ButtonDefaults.buttonColors(containerColor = colorBack),
                    enabled = validation
                ) {
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        stringResource(R.string.next_button),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inputText(
    input: String,
    @DrawableRes icon: Int,
    keyboard: KeyboardType,
    autoCapitalization: KeyboardCapitalization,
    viewModelValue: String,
    onValueChange: (String) -> Unit
) {
    val colorIcon = Color(0xff164583)
    val colorText = Color(0xff043f8a)
    val colorBack = Color(0xffa1cafe)
    val colorLabel = Color(0xff002a61)
    val modifier = when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Modifier
                .padding(8.dp)
                .height(60.dp)
        }

        else -> {
            Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        }
    }

    Icon(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = Modifier.size(48.dp),
        tint = colorIcon
    )
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = viewModelValue,
        onValueChange = { onValueChange(it) },
        label = { Text(input) },
        maxLines = 1,
        textStyle = TextStyle(
            color = colorText,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorText,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = colorText,
            containerColor = colorBack,
            focusedLabelColor = colorLabel
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboard,
            imeAction = ImeAction.Next,
            capitalization = autoCapitalization
        ),// Cambia "Enter" a "Siguiente"
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        modifier = modifier
    )
}

@Composable
fun radioGender(infoViewModel: InfoViewModel = viewModel()) {
    val infoUiState by infoViewModel.uiState.collectAsState()

    val colorIcon = Color(0xff164583)
    val genderFemale = stringResource(R.string.gender_female)
    val genderMale = stringResource(R.string.gender_male)

    Icon(
        painter = painterResource(id = R.drawable.round_group_24),
        contentDescription = null,
        modifier = Modifier.size(48.dp),
        tint = colorIcon
    )
    Spacer(modifier = Modifier.width(20.dp))
    Text(text = stringResource(id = R.string.gender))
    Spacer(modifier = Modifier.width(16.dp))
    //var sex by remember { mutableStateOf(genderFemale) }
    RadioButton(
        selected = infoUiState.gender === genderFemale,
        onClick = { infoViewModel.setGender(genderFemale) }
    )
    Text(text = stringResource(id = R.string.gender_female))
    RadioButton(
        selected = infoUiState.gender === genderMale,
        onClick = { infoViewModel.setGender(genderMale) }
    )
    Text(text = stringResource(id = R.string.gender_male))
}

@Composable
fun selectBirthday() {
    val colorIcon = Color(0xff164583)
    Icon(
        painter = painterResource(id = R.drawable.round_calendar_month_24),
        contentDescription = null,
        modifier = Modifier.size(48.dp),
        tint = colorIcon
    )
    Spacer(modifier = Modifier.width(20.dp))
    Text(text = stringResource(id = R.string.birthdate) + "*")
    Spacer(modifier = Modifier.width(16.dp))
    selectDatePicker();
}

@Composable
fun selectStudy() {
    val colorIcon = Color(0xff164583)
    Icon(
        painter = painterResource(id = R.drawable.round_school_24),
        contentDescription = null,
        modifier = Modifier.size(48.dp),
        tint = colorIcon
    )
    Spacer(modifier = Modifier.width(20.dp))
    schoolDropdownMenu();
    Spacer(modifier = Modifier.width(10.dp))
}

@Composable
fun selectDatePicker(infoViewModel: InfoViewModel = viewModel()) {
    val infoUiState by infoViewModel.uiState.collectAsState()

    val colorIcon = Color(0xffa1cafe)
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val labelBirthdate: String
    if (infoUiState.birthdate.isNullOrEmpty()) {
        labelBirthdate = stringResource(id = R.string.select_button)
    } else {
        labelBirthdate = infoUiState.birthdate
    }

    val mDate = remember { mutableStateOf(value = labelBirthdate) }
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            infoViewModel.setBirthdate(mDate.value)
        }, mYear, mMonth, mDay
    )
    Button(onClick = {
        mDatePickerDialog.show()
    }, colors = ButtonDefaults.buttonColors(containerColor = colorIcon)) {
        Text(labelBirthdate, color = Color.Black)
    }
}

@Composable
fun schoolDropdownMenu(infoViewModel: InfoViewModel = viewModel()) {
    val infoUiState by infoViewModel.uiState.collectAsState()

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var grade by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        }
    )
    {
        val colorText = Color(0xff043f8a)
        val colorBack = Color(0xffa1cafe)
        val colorLabel = Color(0xff002a61)
        val label: String
        if (infoUiState.scholarship.isNullOrEmpty()) {
            label = stringResource(id = R.string.scolarity)
        } else {
            label = infoUiState.scholarship
        }
        TextField(
            value = grade,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = label)
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
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            val items = listOf(
                stringResource(id = R.string.scolarity_primary),
                stringResource(id = R.string.scolarity_secundary),
                stringResource(id = R.string.scolarity_bachelor),
                stringResource(id = R.string.scolarity_other)
            )
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        grade = item
                        isExpanded = false
                        infoViewModel.setScholarship(item)
                    },
                    text = {
                        Text(text = item)
                    }
                )
            }
        }
    }
}
