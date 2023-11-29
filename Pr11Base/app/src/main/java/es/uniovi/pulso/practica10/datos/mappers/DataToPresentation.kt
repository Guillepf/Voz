package es.uniovi.pulso.practica10.datos.mappers

import es.uniovi.pulso.practica10.datos.modelos.MovieEntity
import es.uniovi.pulso.practica10.datos.modelos.MovieResponse
import es.uniovi.pulso.practica10.datos.modelos.Movie

//Son funciones extensoras: Las añadimos a las clases.

//  fun NombreDeLaClase.NombreDeLaFuncion(....) : .... { .... }


//Fíjate que aquí está la url a calzador, cuando debería estar en una clase externa.
//Simplemente por motivos de explicación.

fun MovieResponse.toPresentation() : Movie {
    return Movie(
        id,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/original/${posterPath}",
        "https://image.tmdb.org/t/p/original/${backdropPath}"
    )

}

//Si estoy pasando de DB a Presentation, sabemos que es favorita (true). Por defecto el valor es false.
fun MovieEntity.toPresentation() : Movie {
    return Movie(
        id,
        titulo,
        argumento,
        fechaEstreno,
        "https://image.tmdb.org/t/p/original/${urlCaratula}",
        "https://image.tmdb.org/t/p/original/${urlFondo}",
        true
    )
}
