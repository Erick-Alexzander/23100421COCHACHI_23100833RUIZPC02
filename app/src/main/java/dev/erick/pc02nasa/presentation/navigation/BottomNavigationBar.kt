package dev.erick.pc02nasa.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Apod : BottomNavItem("apod", "Imágenes", Icons.Default.Image)
    object Asteroids : BottomNavItem("asteroids", "Asteroides", Icons.Default.Public)
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Apod,
        BottomNavItem.Asteroids
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Ocultar la barra inferior en las pantallas de detalle o favoritos si se prefiere.
    // De acuerdo a requerimientos es mantener el estado correcto de la barra inferior al cambiar de pantalla,
    // es buena práctica ocultarlo en "asteroid_detail/{id}" y "asteroid_favorites".
    val showBottomBar = currentRoute in listOf(BottomNavItem.Apod.route, BottomNavItem.Asteroids.route)

    if (showBottomBar) {
        NavigationBar {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            // Evitar la construcción de una pila de navegación infinita
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
