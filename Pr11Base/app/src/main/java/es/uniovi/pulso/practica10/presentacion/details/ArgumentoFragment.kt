package es.uniovi.pulso.practica10.presentacion.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.presentacion.MainActivity
import es.uniovi.pulso.practica10.presentacion.MoviesViewModel


class ArgumentoFragment : Fragment(){

    private lateinit var tvArgumento : TextView

    lateinit var viewModel : MoviesViewModel


    private lateinit var botonLeerArgumento : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = (activity as MainActivity).viewModel
        val root = inflater.inflate(R.layout.fragment_movie_argumento, container, false)


        root.let {
            tvArgumento = it.findViewById(R.id.text_argumento)
            botonLeerArgumento = it.findViewById(R.id.button_tts_argumento)
        }

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            tvArgumento.text = it.argumento
        })

        botonLeerArgumento.setOnClickListener {
            leerArgumento()
        }

        return root

    }


    fun leerArgumento() {

        val text = tvArgumento.text.toString()
        /*
        * Pedimos que hable,empleando el método speak. Este método, recibirá:
        * 1. El texto.
        *
        * 2. Qué hacer si el tts está funcionando (en la app):
        *   - TextToSpeech.QUEUE_FLUSH -> Finaliza y empieza con el actual.
        *   - TextToSpeech.QUEUE_ADD -> Añade a la cola el audio actual.
        *
        * 3. Un bundle con parámetros de configuración del motor (Engine). Puede ser null
        *
        * 4. Identificador de la petición.
         */
        
    }


}