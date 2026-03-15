package com.example.internshipdiary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiaryEntry(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: String,
    val hours: Int,
    val task: String,
    val learning: String
)