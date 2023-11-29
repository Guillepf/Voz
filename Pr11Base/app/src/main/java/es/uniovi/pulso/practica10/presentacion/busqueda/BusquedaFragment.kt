package es.uniovi.pulso.practica10.presentacion.busqueda

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.datos.modelos.Movie
import es.uniovi.pulso.practica10.presentacion.MainActivity
import es.uniovi.pulso.practica10.presentacion.MoviesViewModel
import es.uniovi.pulso.practica10.presentacion.adapters.MovieAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [BusquedaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BusquedaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var viewModel : MoviesViewModel

    private lateinit var recyclerViewMovies : RecyclerView

    private lateinit var movieAdapter : MovieAdapter

    private lateinit var btSearch : Button

    private lateinit var txQuery : EditText

    private lateinit var ibVoz : ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        val root = inflater.inflate(R.layout.fragment_busqueda, container, false)

        recyclerViewMovies = root.findViewById(R.id.recyclerMovies)
        recyclerViewMovies.layoutManager = LinearLayoutManager(context)
        recyclerViewMovies.setHasFixedSize(true)
        movieAdapter = MovieAdapter{mostrarMovie(it)}
        recyclerViewMovies.adapter = movieAdapter

        txQuery = root.findViewById(R.id.edit_text_busqueda)

        btSearch = root.findViewById(R.id.button_buscar)

        btSearch.setOnClickListener {
            viewModel.searchMovies(txQuery.text.toString())
        }

        viewModel.busquedaMovies.observe(viewLifecycleOwner, Observer { listaPeliculas ->
            movieAdapter.update(listaPeliculas)
        })


        ibVoz = root.findViewById(R.id.button_escuchar)
        ibVoz.setOnClickListener{
            busquedaPorVoz()
        }

        // Inflate the layout for this fragment
        return root
    }



    private fun busquedaPorVoz(){
        //Inicializa la actividad de búsqueda por Voz.
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
            "¿Qué película está buscando?"
        )
        //No quiero complicaros el código, por eso utilizo el deprecated.
        // Alternativa: registerForActivityResult
        //https://developer.android.com/training/basics/intents/result?hl=es-419

        startActivityForResult(intent,3300)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //Lo ideal sería comprobar con un if que el requestCode es el 3300.
        //Así identificaríamos la actividad de reconocimiento
        //Fíjate que arriba la lanzamos con el 3300.


        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            //EJERCICIO: Mediante data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            //Piensa ¿Por qué es una lista?
            //Recuperamos los resultados de la voz.
            //Recupera el texto y asígnalo a textoEntrada (Es el EditText).
            //NO tienes que hacer la búsqueda. Simplemente pon el texto en el EditText de búsqueda.

            txQuery.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0).toString())

        }
    }

    /**
     * Navegamos al fragmento Details
     */
    private fun mostrarMovie(movie : Movie) {
        val bundle = Bundle()
        bundle.putInt("MOVIE_ID",movie.id)
        findNavController().navigate(
            R.id.action_busquedaFragment_to_detailsFragment,
            bundle
        )
    }
}