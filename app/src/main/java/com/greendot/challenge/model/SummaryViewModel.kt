package com.greendot.challenge.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class SummaryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SummaryRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allSummaries: LiveData<List<Summary>>

    init {
        val summariesDao = SummaryRoomDatabase.getDatabase(application, viewModelScope).summaryDao()
        repository = SummaryRepository(summariesDao)
        allSummaries = repository.allSummaries
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(summary: Summary) = viewModelScope.launch {
        repository.insert(summary)
    }
}