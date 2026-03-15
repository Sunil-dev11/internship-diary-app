package com.example.internshipdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.internshipdiary.screens.AddEntryScreen
import com.example.internshipdiary.screens.DashboardScreen
import com.example.internshipdiary.screens.EntriesScreen
import com.example.internshipdiary.ui.theme.InternshipDiaryTheme
import com.example.internshipdiary.viewmodel.DiaryViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InternshipDiaryTheme {
                InternshipDiaryApp()
            }
        }
    }
}

@Composable
fun InternshipDiaryApp() {

    val navController = rememberNavController()
    val viewModel: DiaryViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {

        composable("dashboard") {
            DashboardScreen(navController, viewModel)
        }

        composable("add") {
            AddEntryScreen(navController, viewModel)
        }

        composable("edit/{entryId}") { backStackEntry ->

            val entryId = backStackEntry.arguments?.getString("entryId")!!.toInt()

            val entries by viewModel.entries.collectAsState(initial = emptyList())

            val entry = entries.find { it.id == entryId }

            AddEntryScreen(
                navController = navController,
                viewModel = viewModel,
                existingEntry = entry
            )
        }

        composable("entries") {
            EntriesScreen(navController, viewModel)
        }
    }
}