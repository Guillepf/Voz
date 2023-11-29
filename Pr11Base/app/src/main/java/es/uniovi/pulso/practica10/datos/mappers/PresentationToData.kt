package es.uniovi.pulso.practica10.datos.mappers

import es.uniovi.pulso.practica10.datos.modelos.MovieEntity
import es.uniovi.pulso.practica10.datos.modelos.Movie

//En este proyecto, simplemente contemplamos el guardado en BD.
//Es decir, de "Movie" -> "MovieEntity"

//Fíjate que cortamos la URL para quedarnos simplemente con el nombre del fichero.

fun Movie.toDataEntity() : MovieEntity {
    return MovieEntity(
        id,
        titulo,
        argumento,
        fechaEstreno,
        urlCaratula.split("/").last(),
        urlFondo.split("/").last(),
    )
}

