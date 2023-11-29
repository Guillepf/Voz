package es.uniovi.pulso.practica10.datos.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    //Singleton
    companion object {

        //Construímos la interacción con TheMovieDB.
        //
        //Recuerda: by lazy implica que se ejecutará ese código
        //  la primera vez que se vaya a utilizar.
        private val retrofitMovies by lazy {
            // Interceptor para ver el log de peticiones
            val logging  = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }

        //Acceso público.
        val moviesDbApi by lazy {
            retrofitMovies.create(TheMovieDbApi::class.java)
        }


    }
}