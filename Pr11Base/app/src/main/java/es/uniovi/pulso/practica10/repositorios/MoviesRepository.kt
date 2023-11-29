package es.uniovi.pulso.practica10.repositorios

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import es.uniovi.pulso.practica10.datos.database.MoviesDataBase
import es.uniovi.pulso.practica10.datos.modelos.MovieEntity
import es.uniovi.pulso.practica10.datos.mappers.toPresentation
import es.uniovi.pulso.practica10.datos.network.RetrofitInstance
import es.uniovi.pulso.practica10.datos.modelos.Interprete
import es.uniovi.pulso.practica10.datos.modelos.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MoviesRepository(
    val moviesDb : MoviesDataBase
) {
    private val API = "6bc4475805ebbc4296bcfa515aa8df08"


    /*
     * Recupera de retrofit la lista de películas populares.
     */
    suspend fun getPopularMovies() : List<Movie> {
        return withContext(Dispatchers.IO){
            //La llamada a retrofit.
            val respuesta = RetrofitInstance.moviesDbApi.listMovies(
                "popular",
                API,
                "es-ES",
                1
            )
            /*
             * Si hay resultados, convertimos cada MovieResponse en una Movie
             * y devolvemos la lista.
             */
            if (respuesta.body() != null)
                respuesta.body()!!.results.map{ it.toPresentation()}
            else
                emptyList()
        }
    }


    /*
     * Devuelve una película dada una id.
     * Si la película está en la base de datos, devolvemos la info.
     * En caso contrario, la recuperamos de la API mediante retrofit
     */
    suspend fun getMovie(movieId : Int) : Movie {
        return withContext(Dispatchers.IO){
            val movieEntity = moviesDb.movieDao().findById(movieId)
            if (movieEntity != null)
                movieEntity.toPresentation()
            else
            {
                val respuesta = RetrofitInstance.moviesDbApi.getMovie(
                    movieId,
                    API,
                    "es-ES"
                )
                respuesta.body()!!.toPresentation()
            }


        }
    }

    fun getFavoriteMovies() : LiveData<List<Movie>> {
        //¡Ojo! El PRIMER map se hace sobre un LiveData y es gracias a:
        //      import androidx.lifecycle.map
        //Mapeamos de LiveData<List<MovieEntity>> a LiveData<List<Movie>>
        //El primer map, recibe la lista.
        //El segundo map, cada elemento de la lista.
        return moviesDb.movieDao().getAll().map{
                listMovieEntities -> listMovieEntities.map{
                movieEntity -> movieEntity.toPresentation()
        }
        }
    }

    /*
     * Borra una película de la base de datos.
     */
    fun deleteMovieFromFavorites(movie : MovieEntity) {
        moviesDb.movieDao().delete(movie)
    }

    /*
     * Elimina todas las películas favoritas de la base de datos.
     */
    fun deleteAllMoviesFromFavorites() {
        moviesDb.movieDao().deleteAll()
    }

    /*
     * Añade una película a favoritos.
     */
    fun addMovieToFavorites(movie: MovieEntity) {
        moviesDb.movieDao().add(movie)
    }


    /*
     * Recupera de Retrofit el reparto de la película dada una id
     */
    suspend fun getMovieCredits(movieId : Int) : List<Interprete> {
        return withContext(Dispatchers.IO){
            val respuesta = RetrofitInstance.moviesDbApi.getMovieCredits(
                movieId,
                API,
                "es-ES"
            )
            if (respuesta.body() != null)
                respuesta.body()!!.reparto
            else
                emptyList()

        }
    }


    suspend fun searchMovies(busqueda: String) : List<Movie> {
        return emptyList()
    }

}