package com.pdmcourse2026.basictemplate.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdmcourse2026.basictemplate.data.repository.RankeUcaApi
import com.pdmcourse2026.basictemplate.data.repository.RankeUcaApiImpl
import com.pdmcourse2026.basictemplate.model.options
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository: RankeUcaApi = RankeUcaApiImpl()

    private val _uiState = MutableStateFlow<List<options>>(emptyList())
    val uiState: StateFlow<List<options>> = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadOptions() {
        viewModelScope.launch {
            _error.value = null
            _loading.value = true
            repository.getOptions()
                .onSuccess { optionsList ->
                    _uiState.value = optionsList
                }
                .onFailure { e ->
                    _error.value = "Error al cargar el menú: ${e.message}"
                }
            _loading.value = false
        }
    }

    fun vote(optionId: Int) {
        viewModelScope.launch {
            repository.vote(optionId)
                .onSuccess {
                    loadOptions() // Refresh list after voting
                }
                .onFailure { e ->
                    _error.value = "Error al votar: ${e.message}"
                }
        }
    }
}
