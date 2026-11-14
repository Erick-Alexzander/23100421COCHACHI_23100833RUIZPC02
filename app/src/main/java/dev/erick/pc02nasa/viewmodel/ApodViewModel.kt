package dev.erick.pc02nasa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.erick.pc02nasa.model.ApodItem
import dev.erick.pc02nasa.repository.NasaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ApodUiState {
    object Loading : ApodUiState
    data class Success(val items: List<ApodItem>) : ApodUiState
    data class Error(val message: String) : ApodUiState
}

class ApodViewModel(
    private val repository: NasaRepository = NasaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ApodUiState>(ApodUiState.Loading)
    val uiState: StateFlow<ApodUiState> = _uiState.asStateFlow()

    init {
        fetchLast7DaysApod()
    }

    fun fetchLast7DaysApod() {
        viewModelScope.launch {
            _uiState.value = ApodUiState.Loading
            try {
                val items = repository.getLast7DaysApod()
                val sortedItems = items.sortedByDescending { it.date }
                _uiState.value = ApodUiState.Success(sortedItems)
            } catch (e: Exception) {
                _uiState.value = ApodUiState.Error(e.localizedMessage ?: "Error desconocido al obtener APOD")
            }
        }
    }
}
