package dev.erick.pc02nasa.presentation.asteroids

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.erick.pc02nasa.data.local.entity.AsteroidEntity
import dev.erick.pc02nasa.data.repository.AsteroidRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AsteroidViewModel(
    private val repository: AsteroidRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _filterHazardous = MutableStateFlow(false)
    val filterHazardous: StateFlow<Boolean> = _filterHazardous.asStateFlow()

    val asteroids: StateFlow<List<AsteroidEntity>> = combine(
        repository.getAllAsteroids(),
        _filterHazardous
    ) { list, hazardousOnly ->
        if (hazardousOnly) list.filter { it.isPotentiallyHazardous } else list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteAsteroids: StateFlow<List<AsteroidEntity>> = repository.getFavoriteAsteroids()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        syncData()
    }

    fun syncData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                repository.syncAsteroids()
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión. Se muestran los datos almacenados."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleHazardousFilter(enabled: Boolean) {
        _filterHazardous.value = enabled
    }

    fun toggleFavorite(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteStatus(id, isFavorite)
        }
    }

    fun getAsteroidById(id: String): StateFlow<AsteroidEntity?> {
        return repository.getAsteroidById(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }
}
