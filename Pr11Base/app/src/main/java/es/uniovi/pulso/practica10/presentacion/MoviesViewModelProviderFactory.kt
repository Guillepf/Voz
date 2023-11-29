package es.uniovi.pulso.practica10.presentacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.uniovi.pulso.practica10.repositorios.MoviesRepository

/*
    Más info en:
    https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories?hl=es-419
    ¿Crees que es mejorable el código?
 */
class MoviesViewModelProviderFactory(
    val moviesRepository: MoviesRepository
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(moviesRepository) as T
    }
}