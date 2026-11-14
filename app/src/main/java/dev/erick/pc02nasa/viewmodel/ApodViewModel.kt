package dev.erick.pc02nasa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.erick.pc02nasa.data.local.entity.ApodEntity
import dev.erick.pc02nasa.model.ApodItem
import dev.erick.pc02nasa.repository.NasaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface ApodUiState {
    object Loading : ApodUiState
    data class Success(val items: List<ApodItem>) : ApodUiState
    data class Error(val message: String) : ApodUiState
}

class ApodViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NasaRepository(context = application)

    private val _uiState = MutableStateFlow<ApodUiState>(ApodUiState.Loading)
    val uiState: StateFlow<ApodUiState> = _uiState.asStateFlow()

    val favoriteApods: StateFlow<List<ApodEntity>> = repository.favoriteApods
        ?.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        ?: MutableStateFlow(emptyList())

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

    fun getApodByDate(date: String): ApodItem? {
        val state = _uiState.value
        if (state is ApodUiState.Success) {
            val foundInApi = state.items.find { it.date == date }
            if (foundInApi != null) return foundInApi
        }
        val favoriteEntity = favoriteApods.value.find { it.date == date }
        return favoriteEntity?.toItem()
    }

    fun isFavorite(date: String): Boolean {
        return favoriteApods.value.any { it.date == date }
    }

    fun toggleFavorite(item: ApodItem) {
        viewModelScope.launch {
            val date = item.date ?: return@launch
            val existing = favoriteApods.value.find { it.date == date }
            if (existing != null) {
                repository.removeFavorite(existing)
            } else {
                repository.insertFavorite(item.toEntity())
            }
        }
    }
}

fun ApodItem.toEntity(): ApodEntity {
    return ApodEntity(
        date = this.date ?: "",
        title = this.title,
        url = this.url,
        explanation = this.explanation,
        hdurl = this.hdurl,
        mediaType = this.mediaType,
        copyright = this.copyright
    )
}

fun ApodEntity.toItem(): ApodItem {
    return ApodItem(
        date = this.date,
        title = this.title,
        url = this.url,
        explanation = this.explanation,
        hdurl = this.hdurl,
        mediaType = this.mediaType,
        serviceVersion = null,
        copyright = this.copyright
    )
}
