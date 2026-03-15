package com.example.internshipdiary.data

import androidx.room.*
import com.example.internshipdiary.model.DiaryEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Insert
    suspend fun insertEntry(entry: DiaryEntry)

    @Delete
    suspend fun deleteEntry(entry: DiaryEntry)

    @Update
    suspend fun updateEntry(entry: DiaryEntry)

    @Query("SELECT * FROM DiaryEntry ORDER BY id DESC")
    fun getEntries(): Flow<List<DiaryEntry>>

}