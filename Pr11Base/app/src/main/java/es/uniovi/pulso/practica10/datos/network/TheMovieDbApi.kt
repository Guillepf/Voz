package es.uniovi.pulso.practica10.datos.network

import es.uniovi.pulso.practica10.datos.modelos.MovieCreditsResult
import es.uniovi.pulso.practica10.datos.modelos.MovieListResult
import es.uniovi.pulso.practica10.datos.modelos.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {

    //https://api.themoviedb.org/3/search/movie?query={query}&api_key={código}&language=es-ES&page=1
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query : String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<MovieListResult>


    // https://api.themoviedb.org/3/movie/popular?api_key={codigo}&language=es-ES&page=1
    @GET("movie/{type}")
    suspend fun listMovies(
        @Path("type") type: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieListResult>



    // https://api.themoviedb.org/3/movie/{id}?api_key={código}&language=es-ES&append_to_response=videos
    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String,
        @Query("language") language: String
    ) : Response<MovieResponse>


    /**
     * Devuelve el cast de una película (Entidad: MovieCreditsResult), que está compuesto por:
     * - Una lista de intérpetes (reparto: List<Interprete>)
     * - Una lista de personal (personal: List<CrewMember>)
     *
     * https://api.themoviedb.org/3/movie/{id}/credits?api_key={código}&language=es-ES
     */
    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") id : Int,
        @Query("api_key") apiKey : String,
        @Query("language") language: String
    ): Response<MovieCreditsResult>
}