package com.example.internshipdiary.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.internshipdiary.viewmodel.DiaryViewModel
import androidx.compose.material.icons.filled.Sort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntriesScreen(
    navController: NavController,
    viewModel: DiaryViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    var sortDescending by remember { mutableStateOf(true) }
    val entries by viewModel.entries.collectAsState()

    val sortedEntries =
        if (sortDescending)
            entries.sortedByDescending { it.date }
        else
            entries.sortedBy { it.date }

    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(

                title = { Text("Entries") },

                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },

                actions = {

                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.Sort, contentDescription = "Sort")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {

                        DropdownMenuItem(
                            text = { Text("Newest First") },
                            onClick = {
                                sortDescending = true
                                expanded = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Oldest First") },
                            onClick = {
                                sortDescending = false
                                expanded = false
                            }
                        )
                    }
                }
            )
        },

        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(sortedEntries) { entry ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Text(
                                text = entry.date,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Row {

                                IconButton(
                                    onClick = {
                                        navController.navigate("edit/${entry.id}")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Entry",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.deleteEntry(entry) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Entry",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Hours: ${entry.hours}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Task: ${entry.task}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Learning: ${entry.learning}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }

                }

            }

        }

    }

}