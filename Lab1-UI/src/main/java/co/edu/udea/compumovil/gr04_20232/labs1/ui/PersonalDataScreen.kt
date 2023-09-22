@file:OptIn(ExperimentalMaterial3Api::class)

package co.edu.udea.compumovil.gr04_20232.labs1.ui

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.icu.util.Calendar
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import co.edu.udea.compumovil.gr04_20232.labs1.InfoViewModel
import co.edu.udea.compumovil.gr04_20232.labs1.R

@Composable
fun PersonalDataScreen(onNextButton: (Int) -> Unit, viewModel: InfoViewModel) {
    val screenOrientation = LocalConfiguration.current.orientation
    when (screenOrientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            personalDataHorizontalLayout(onNextButton, viewModel)
        }
        Configuration.ORIENTATION_PORTRAIT -> {
            personalDataVerticalLayout(onNextButton, viewModel)
        }
        else -> {
            personalDataVerticalLayout(onNextButton, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun personalDataHorizontalLayout(onNextButton: (Int) -> Unit, viewModel: InfoViewModel) {
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
                    .padding(top = 5.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                inputText(
                    stringResource(id = R.string.firstname),
                    R.drawable.round_person_24,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    viewModel.firstName,
                    onValueChange = { newValue ->
                        viewModel.firstName = newValue
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                inputText(
                    stringResource(id = R.string.lastname),
                    R.drawable.round_person_add_24,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    viewModel.lastName,
                    onValueChange = { newValue ->
                        viewModel.lastName = newValue
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,

                ) {
                radioGender(viewModel)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                selectBirthday(viewModel)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                selectStudy(viewModel)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            )
            {
                val colorBack = Color(0xffa1cafe)
                val colorText = Color(0xff043f8a)
                Button(
                    onClick = {
                        onNextButton(2)
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
fun personalDataVerticalLayout(onNextButton: (Int) -> Unit, viewModel: InfoViewModel) {
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
                    stringResource(id = R.string.firstname),
                    R.drawable.round_person_24,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    viewModel.firstName,
                    onValueChange = { newValue ->
                        viewModel.firstName = newValue
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                inputText(
                    stringResource(id = R.string.lastname),
                    R.drawable.round_person_add_24,
                    KeyboardType.Text,
                    KeyboardCapitalization.Words,
                    viewModel.lastName,
                    onValueChange = { newValue ->
                        viewModel.lastName = newValue
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                radioGender(viewModel)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                selectBirthday(viewModel)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                selectStudy(viewModel)
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
    val screenOrientation = LocalConfiguration.current.orientation
    val modifier = when (screenOrientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        }
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
fun radioGender(viewModel: InfoViewModel) {
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
        selected = viewModel.gender === genderFemale,
        onClick = {
            viewModel.gender = genderFemale
        }
    )
    Text(text = stringResource(id = R.string.gender_female))
    RadioButton(
        selected = viewModel.gender === genderMale,
        onClick = {
            viewModel.gender = genderMale
        }
    )
    Text(text = stringResource(id = R.string.gender_male))
}

@Composable
fun selectBirthday(viewModel: InfoViewModel) {
    val colorIcon = Color(0xff164583)
    Icon(
        painter = painterResource(id = R.drawable.round_calendar_month_24),
        contentDescription = null,
        modifier = Modifier.size(48.dp),
        tint = colorIcon
    )
    Spacer(modifier = Modifier.width(20.dp))
    Text(text = stringResource(id = R.string.birthdate))
    Spacer(modifier = Modifier.width(16.dp))
    selectDatePicker(viewModel);
}

@Composable
fun selectStudy(viewModel: InfoViewModel) {
    val colorIcon = Color(0xff164583)
    Icon(
        painter = painterResource(id = R.drawable.round_school_24),
        contentDescription = null,
        modifier = Modifier.size(48.dp),
        tint = colorIcon
    )
    Spacer(modifier = Modifier.width(20.dp))
    schoolDropdownMenu(viewModel);
    Spacer(modifier = Modifier.width(10.dp))
}

@Composable
fun selectDatePicker(viewModel: InfoViewModel) {
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
    if (viewModel.birthdate.isNullOrEmpty()) {
        labelBirthdate = stringResource(id = R.string.select_button)
    } else {
        labelBirthdate = viewModel.birthdate
    }

    //val selectButtonString = stringResource(id = R.string.select_button)
    val mDate = remember { mutableStateOf(value = labelBirthdate) }
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            viewModel.birthdate = mDate.value
        }, mYear, mMonth, mDay
    )
    Button(onClick = {
        mDatePickerDialog.show()
    }, colors = ButtonDefaults.buttonColors(containerColor = colorIcon)) {
        Text(labelBirthdate, color = Color.Black)
    }
}

@Composable
fun schoolDropdownMenu(viewModel: InfoViewModel) {
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
        if (viewModel.scolarity.isNullOrEmpty()) {
            label = stringResource(id = R.string.scolarity)
        } else {
            label = viewModel.scolarity
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
            val items = listOf(stringResource(id = R.string.scolarity_primary), stringResource(id = R.string.scolarity_secundary), stringResource(id = R.string.scolarity_bachelor), stringResource(id = R.string.scolarity_other))
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        grade = item
                        isExpanded = false
                        viewModel.scolarity = item
                    },
                    text = {
                        Text(text = item)
                    }
                )
            }
/*
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.scolarity_primary))
                },
                onClick = {
                    grade = labelScolarityPrimary
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.scolarity_secundary))
                },
                onClick = {
                    grade = labelScolaritySecundary
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.scolarity_bachelor))
                },
                onClick = {
                    grade = labelScolarityBachelor
                    isExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.scolarity_other))
                },
                onClick = {
                    grade = labelScolarityOther
                    isExpanded = false
                }
            )*/
        }
    }
}
