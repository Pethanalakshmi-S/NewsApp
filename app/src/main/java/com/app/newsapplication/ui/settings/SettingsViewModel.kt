package com.app.newsapplication.ui.settings

import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

@HiltViewModel
class SettingsViewModel(val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _theme = MutableLiveData("Auto")
    val theme: LiveData<String> = _theme

    fun onThemeChanged(newTheme: String) {
        when (newTheme) {
            "Auto" -> _theme.value = "Light"
            "Light" -> _theme.value = "Dark"
            "Dark" -> _theme.value = "Auto"
        }
    }
}