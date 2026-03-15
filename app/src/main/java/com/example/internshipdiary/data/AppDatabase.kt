package com.example.internshipdiary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.internshipdiary.model.DiaryEntry

@Database(entities = [DiaryEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}