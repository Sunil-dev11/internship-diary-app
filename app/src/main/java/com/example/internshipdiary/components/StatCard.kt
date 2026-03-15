package com.example.internshipdiary.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun StatCard(
    title: String,
    value: Int,
    icon: ImageVector
) {

    val animatedValue by animateIntAsState(
        targetValue = value,
        label = ""
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .width(160.dp)
            .padding(6.dp)
//            .animateContentSize()
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {

                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = animatedValue.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF2962FF)
                )
            }
        }
    }
}

@Composable
fun ProgressCircleSection(progress: Float) {

    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Internship Progress",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    progress = progress,
                    strokeWidth = 10.dp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(120.dp)
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Completion toward 100 hours goal")

        }
    }
}