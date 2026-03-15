package com.example.internshipdiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipdiary.data.DatabaseProvider
import com.example.internshipdiary.model.DiaryEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class DiaryViewModel(application: Application) : AndroidViewModel(application) {

    private val dao =
        DatabaseProvider.getDatabase(application).diaryDao()

    val entries = dao.getEntries().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun addEntry(entry: DiaryEntry) {

        viewModelScope.launch {
            dao.insertEntry(entry)
        }

    }

    fun deleteEntry(entry: DiaryEntry) {

        viewModelScope.launch {
            dao.deleteEntry(entry)
        }

    }

    fun updateEntry(entry: DiaryEntry) {
        viewModelScope.launch {
            dao.updateEntry(entry)
        }
    }
}