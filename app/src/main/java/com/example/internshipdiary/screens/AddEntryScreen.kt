package com.example.internshipdiary.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.internshipdiary.model.DiaryEntry
import com.example.internshipdiary.viewmodel.DiaryViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(
    navController: NavController,
    viewModel: DiaryViewModel,
    existingEntry: DiaryEntry? = null
) {

    var date by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }
    var task by remember { mutableStateOf("") }
    var learning by remember { mutableStateOf("") }

    LaunchedEffect(existingEntry) {
        existingEntry?.let {
            date = it.date
            hours = it.hours.toString()
            task = it.task
            learning = it.learning
        }
    }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        navController.context,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth-${month + 1}-$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (existingEntry == null)
                            "Add Internship Entry"
                        else
                            "Edit Internship Entry"
                    )
                },

                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            OutlinedTextField(
                value = date,
                onValueChange = {},
                label = { Text("Select Date") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            Button(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Pick Date")
            }

            OutlinedTextField(
                value = hours,
                onValueChange = { hours = it },
                label = { Text("Hours Worked") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = task,
                onValueChange = { task = it },
                label = { Text("Task Performed") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = learning,
                onValueChange = { learning = it },
                label = { Text("Learning") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {

                    val entry = DiaryEntry(
                        id = existingEntry?.id ?: 0,
                        date = date,
                        hours = hours.toIntOrNull() ?: 0,
                        task = task,
                        learning = learning
                    )

                    if (existingEntry == null)
                        viewModel.addEntry(entry)
                    else
                        viewModel.updateEntry(entry)

                    navController.popBackStack()

                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    if (existingEntry == null)
                        "Save Entry"
                    else
                        "Update Entry"
                )
            }

        }

    }
}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun AddEntryScreenPreview() {

    val navController = androidx.navigation.compose.rememberNavController()

    val fakeViewModel = object : DiaryViewModel(
        android.app.Application()
    ) {}

    AddEntryScreen(
        navController = navController,
        viewModel = fakeViewModel
    )

}