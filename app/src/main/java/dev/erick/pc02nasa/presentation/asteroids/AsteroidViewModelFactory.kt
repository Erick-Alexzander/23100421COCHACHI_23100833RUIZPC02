package dev.erick.pc02nasa.presentation.asteroids

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.erick.pc02nasa.data.local.dao.AsteroidDao
import dev.erick.pc02nasa.data.remote.NeoWsService
import dev.erick.pc02nasa.data.repository.AsteroidRepository

class AsteroidViewModelFactory(
    private val api: NeoWsService,
    private val dao: AsteroidDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AsteroidViewModel(AsteroidRepository(api, dao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
