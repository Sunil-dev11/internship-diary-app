package com.example.internshipdiary.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.internshipdiary.model.DiaryEntry
import com.example.internshipdiary.viewmodel.DiaryViewModel

@Composable
fun EntryFormScreen(viewModel: DiaryViewModel) {

    var date by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }
    var task by remember { mutableStateOf("") }
    var learning by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = hours,
            onValueChange = { hours = it },
            label = { Text("Hours") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = task,
            onValueChange = { task = it },
            label = { Text("Task") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = learning,
            onValueChange = { learning = it },
            label = { Text("Learning") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                val entry = DiaryEntry(
                    date = date,
                    hours = hours.toIntOrNull() ?: 0,
                    task = task,
                    learning = learning
                )

                viewModel.addEntry(entry)

            }
        ) {
            Text("Save Entry")
        }

    }
}