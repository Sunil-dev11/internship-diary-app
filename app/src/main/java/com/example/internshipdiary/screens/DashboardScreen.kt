package com.example.internshipdiary.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.internshipdiary.components.StatCard
import com.example.internshipdiary.viewmodel.DiaryViewModel
import kotlinx.coroutines.launch
import com.example.internshipdiary.components.ProgressCircleSection
import com.example.internshipdiary.components.WeeklyChart
import androidx.compose.material3.FloatingActionButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: DiaryViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val entries by viewModel.entries.collectAsState(initial = emptyList())

    val totalLogs = entries.size
    val totalHours = entries.sumOf { it.hours }
    val avgHours = if (totalLogs > 0) totalHours / totalLogs else 0
    val weeklyAvg =
        if (entries.isNotEmpty())
            entries.takeLast(7).sumOf { it.hours } / entries.takeLast(7).size
        else 0
    val goalHours = 100
    val progress = (totalHours.toFloat() / goalHours).coerceIn(0f, 1f)
    val weeklyHours = entries
        .takeLast(7)
        .map { it.hours.toFloat() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "InternLog",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Dashboard") },
                    selected = true,
                    onClick = {
                        navController.popBackStack("dashboard", false)
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = null)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Add Entry") },
                    selected = false,
                    onClick = {
                        navController.navigate("add")
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Entries") },
                    selected = false,
                    onClick = {
                        navController.navigate("entries")
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(Icons.Default.List, contentDescription = null)
                    }
                )
            }
        }
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("InternLog")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },

            floatingActionButton = {

                FloatingActionButton(
                    onClick = { navController.navigate("add") },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Entry",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

            }

        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 90.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "Hello, Intern!",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Track your internship progress",
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        StatCard("Total Hours", totalHours, Icons.Default.AccessTime)
                        Spacer(modifier = Modifier.width(8.dp))
                        StatCard("Avg Hours", avgHours, Icons.Default.Star)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        StatCard("Weekly Avg", weeklyAvg, Icons.Default.ShowChart)
                        Spacer(modifier = Modifier.width(8.dp))
                        StatCard("Total Logs", totalLogs, Icons.Default.List)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    ProgressCircleSection(progress = progress)
                }

                item {

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Weekly Activity",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    WeeklyChart(
                        hours = weeklyHours,
                        dates = entries.takeLast(7).map { it.date },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    Text("Dashboard Preview")
}