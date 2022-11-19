package com.example.date_time_dialogs__compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.date_time_dialogs__compose.ui.theme.DatetimedialogscomposeTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatetimedialogscomposeTheme {

                var pickedDate by remember {
                    mutableStateOf(LocalDate.now())
                }
                var pickedTime by remember {
                    mutableStateOf(LocalTime.NOON)
                }
                val formattedDate by remember {
                    derivedStateOf {
                        DateTimeFormatter
                            .ofPattern("MMM dd yyyy")
                            .format(pickedDate)
                    }
                }
                val formattedTime by remember {
                    derivedStateOf {
                        DateTimeFormatter
                            .ofPattern("hh:mm")
                            .format(pickedTime)
                    }
                }
                val dateDialogState = rememberMaterialDialogState()
                val timeDialogState = rememberMaterialDialogState()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { dateDialogState.show() }
                    ) {
                        Text(text = "Pick date")
                    }
                    Text(text = formattedDate)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { timeDialogState.show() }
                    ) {
                        Text(text = "Pick time")
                    }
                    Text(text = formattedTime)
                }

                MaterialDialog(
                    dialogState = dateDialogState,
                    border = BorderStroke(2.dp, Color.LightGray),
                    elevation = 22.dp,
                    onCloseRequest = {  },
                    buttons = {
                        positiveButton(text = "Ok") { }
                        negativeButton(text = "Cancel")
                    }
                ) {
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = "Pick a date, dude",
                        colors = DatePickerDefaults.colors(),
                        allowedDateValidator = {
//                            it.dayOfMonth % 2 == 1
                            true
                        }
                    ) {
                        pickedDate = it
                    }
                    ShowSnackbar(pickedDate.toString())
                }

                MaterialDialog(
                    dialogState = timeDialogState,
                    border = BorderStroke(2.dp, Color.LightGray),
                    elevation = 22.dp,
                    onCloseRequest = { },
                    buttons = {
                        positiveButton(text = "Ok") { }
                        negativeButton(text = "Cancel")
                    }
                ) {
                    timepicker(
                        initialTime = LocalTime.NOON,
                        title = "Pick a date, dude",
                        colors = TimePickerDefaults.colors(),
                        timeRange = LocalTime.MIDNIGHT..LocalTime.MAX
                    ) {
                        pickedTime = it

//                        Log.d("MainActivityCheck:::", "" +
//                                ".MIDNIGHT=${LocalTime.MIDNIGHT}" +     // -> 00:00
//                                ".NOON=${LocalTime.NOON}" +             // -> 12:00
//                                ".MAX=${LocalTime.MAX}" +               // -> 23:59:59.9999999999
//                                ".MIN=${LocalTime.MIN}")                // -> 00:00
                    }
                    ShowSnackbar(pickedTime.toString())
                }


            //DatetimedialogscomposeTheme
            }
        // setContent
        }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShowSnackbar(message: String = "DEFAULT YEP !!") {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .background(Color.LightGray)
            .padding(8.dp)
    ) { paddingValues ->
        Button(onClick = {
            coroutineScope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = "Do something"
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> Unit
                    SnackbarResult.ActionPerformed -> Unit
                }
            }
        }) {
            Text(text = "Click me! I'm a Snackbar inside Dialog")
        }
    }
}
