package es.uniovi.pulso.practica10.datos.modelos

/*
    Esta es la clase que es utilizará en la capa de presentación.
    favorita es opcional, por defecto será false.
 */
data class Movie(
    val id : Int,
    val titulo : String,
    val argumento : String,
    val fechaEstreno : String,
    val urlCaratula : String,
    val urlFondo : String,
    val favorita : Boolean = false
)