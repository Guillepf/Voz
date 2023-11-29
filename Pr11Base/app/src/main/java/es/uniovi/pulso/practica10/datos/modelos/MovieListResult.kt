package es.uniovi.pulso.practica10.datos.modelos


import com.google.gson.annotations.SerializedName

data class MovieListResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)