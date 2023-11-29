package es.uniovi.pulso.practica10.presentacion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.uniovi.pulso.practica10.datos.mappers.toDataEntity
import es.uniovi.pulso.practica10.datos.modelos.Interprete
import es.uniovi.pulso.practica10.datos.modelos.Movie
import es.uniovi.pulso.practica10.repositorios.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/* El ciclo del modelo va más alla del ciclo de la actividad
    https://developer.android.com/static/images/topic/libraries/architecture/viewmodel-lifecycle.png?hl=es-419

    Nunca llamaremos directamente al constructor que recibe parámetros.

    Necesitamos utilizar otras vías: Una factoría.

 */
class MoviesViewModel(
    private val moviesRepository: MoviesRepository) : ViewModel() {

    //LiveData y MutableLiveData
    //https://developer.android.com/topic/libraries/architecture/livedata?hl=es-419

    //Películas populares.
    val popularMovies : MutableLiveData<List<Movie>> = MutableLiveData()

    //La película actual (cuando entramos en una película)
    val movie : MutableLiveData<Movie> = MutableLiveData()

    //El reparto de la película actual.
    val movieCredits : MutableLiveData<List<Interprete>> = MutableLiveData()

    //¿Está la película en favoritos?
    val favorita : MutableLiveData<Boolean> = MutableLiveData(false)



    /* Se ejecuta al inicializar el modelo */

    init {
        Log.d("PR11", "Se inicializa el viewModel")
        getPopularMovies()
    }


    /*
     * Recupera del repositorio las películas favoritas.
     */
    fun getPopularMovies() {
        //Utilizamos el viewModelScope. No lo habíamos utilizado antes.
        //Mientras viva el viewModel (recuerda que vive más que la actividad).
        viewModelScope.launch(Dispatchers.IO) {
            val resp = moviesRepository.getPopularMovies()
            if (resp.isNotEmpty()) {
                //Aquí estamos actualizando el atributo popularMovies (que es un LiveData de la lista de películas).
                //Así, las partes de la interfaz que están observando "popularMovies", serán notificadas.
                popularMovies.postValue(resp)
            }
        }
    }


    /**
     * Llama al repositorio para eliminar la  película actual (movie) de favoritos.
     */
    fun removeFromFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            // Fíjate que aquí estamos trabajando con objetos Movie.
            // Sin embargo, al repositorio le estamos enviado Movie transformada a  MovieEntity
            // Ese código está en: datos.mappers
            moviesRepository.deleteMovieFromFavorites(movie.value!!.toDataEntity())
        }
    }

    /*
     * Llama al repositorio para eliminar todas las películas de favoritos.
     */
    fun removeAllFromFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.deleteAllMoviesFromFavorites()
            favorita.postValue(false)
        }
    }


    /*
     * Llama al repositorio para añadir la película actual a favoritos.
     */
    fun addToFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            //Fíjate una vez más: de Movie a MovieEntity
            moviesRepository.addMovieToFavorites(movie.value!!.toDataEntity())
        }
    }

    /*
     * Recupera del repositorio la película a mostrar
     * Recibe la id de la película.
     */
    fun getMovieInfo(movieId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val resp = moviesRepository.getMovie(movieId)
            movie.postValue(resp)
            favorita.postValue(resp.favorita)
        }
    }

    /*
     * Recupera del repositorio el reparto de la película actual.
     */
    fun getMovieCredits() {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = (movie.value)
            val resp = moviesRepository.getMovieCredits(movie!!.id)
            //Una vez más, actualizamos el LiveData.
            movieCredits.postValue(resp)
        }
    }

    /*
     *  Recupera la lista de películas favoritas.
     *  Fíjate que aquí estamos devolviendo directamente un LiveData
     *  Se observará el resultado de esta función en lugar de guardarlo en un atributo.
     *  Simplemente es otra forma de hacer """"lo mismo""""
     */
    fun getFavoriteMovies() : LiveData<List<Movie>> {
        return moviesRepository.getFavoriteMovies()
    }


    /*
     * Llamará al repositorio y recuperará la lista de películas.
     * La lista pondrá en un MutableLiveData (será un atributo)
     */
    fun searchMovies(query: String)  {

    }
}

