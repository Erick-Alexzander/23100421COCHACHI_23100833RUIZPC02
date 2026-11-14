package dev.erick.pc02nasa

import android.app.Application
import dev.erick.pc02nasa.data.local.NasaDatabase

class NasaApplication : Application() {
    
    // Instancia perezosa de la base de datos para asegurar un único origen de verdad
    val database by lazy { NasaDatabase.getDatabase(this) }
    
    override fun onCreate() {
        super.onCreate()
        // Aquí se puede inicializar Timber u otras configuraciones globales
    }
}
