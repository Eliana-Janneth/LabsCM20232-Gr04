@file:OptIn(ExperimentalMaterial3Api::class)

package co.edu.udea.compumovil.gr04_20232.labs1

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import co.edu.udea.compumovil.gr04_20232.labs1.ui.theme.Labs20232Gr04Theme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            app()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun app() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Información Personal",
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
            Icon(
                Icons.Rounded.Person,
                contentDescription = stringResource(id = R.string.personIcon),
                modifier = Modifier.size(48.dp)

            )
            var name by remember { mutableStateOf("") }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Person,
                contentDescription = stringResource(id = R.string.personIcon),
                modifier = Modifier.size(48.dp)

            )
            var lastName by remember { mutableStateOf("") }
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellidos") },
                maxLines = 1,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Face,
                contentDescription = stringResource(id = R.string.personIcon),
                modifier = Modifier.size(48.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Sexo")
            Spacer(modifier = Modifier.width(16.dp))
            var sex by remember { mutableStateOf("female") }
            RadioButton(
                selected = sex === "female",
                onClick = { sex = "female" }
            )
            Text(text = "Femenino")

            RadioButton(
                selected = sex === "male",
                onClick = { sex = "male" }
            )
            Text(text = "Masculino")

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.DateRange,
                contentDescription = stringResource(id = R.string.personIcon),
                modifier = Modifier.size(48.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Fecha de Nacimiento:")
            Spacer(modifier = Modifier.width(16.dp))
            selectDatePicker();

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),


            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                Icons.Rounded.Info,
                contentDescription = stringResource(id = R.string.personIcon),
                modifier = Modifier.size(48.dp)

            )
            Spacer(modifier = Modifier.width(20.dp))
            GenderDropdownMenu();
            Spacer(modifier = Modifier.width(16.dp))

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Spacer(Modifier.weight(1f))
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
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {

                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    "Siguiente",
                    color = Color.Black,
                )
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = "ArrowForward",
                    tint = Color.Black
                )
            }
        }

    }
}


@Composable
fun selectDatePicker() {
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    Button(onClick = {
        mDatePickerDialog.show()
    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
        Text("${mDate.value}", color = Color.Black)
    }
}

@Composable
fun GenderDropdownMenu() {
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
        TextField(
            value = grade,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = "Grado de Escolaridad")
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
            DropdownMenuItem(
                text = {
                    Text(text = "Primaria")
                },
                onClick = {
                    grade = "Primaria"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Secundaria")
                },
                onClick = {
                    grade = "Secundaria"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Universitaria")
                },
                onClick = {
                    grade = "Universitaria"
                    isExpanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = "Otro")
                },
                onClick = {
                    grade = "Otro"
                    isExpanded = false
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Labs20232Gr04Theme {
        Greeting("Android")
    }


}