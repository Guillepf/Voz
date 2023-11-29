package es.uniovi.pulso.practica10.presentacion.populares

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.presentacion.MainActivity
import es.uniovi.pulso.practica10.presentacion.MoviesViewModel
import es.uniovi.pulso.practica10.presentacion.adapters.MovieAdapter
import es.uniovi.pulso.practica10.datos.modelos.Movie


class PopularesFragment : Fragment() {

    lateinit var viewModel : MoviesViewModel

    private lateinit var recyclerViewMovies : RecyclerView

    private lateinit var movieAdapter : MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = (activity as MainActivity).viewModel
        val root = inflater.inflate(R.layout.fragment_populares, container, false)

        recyclerViewMovies = root.findViewById(R.id.recyclerMovies)
        recyclerViewMovies.layoutManager = LinearLayoutManager(context)
        recyclerViewMovies.setHasFixedSize(true)
        movieAdapter = MovieAdapter{mostrarMovie(it)}
        recyclerViewMovies.adapter = movieAdapter

        /*
         * Observamos el popularMovies del viewModel. Cuando se nos notifique un cambio:
         * Actualizamos el adapter.
         */
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { listaPeliculas ->
            movieAdapter.update(listaPeliculas)
        })

        return root
    }

    /**
     * Navegamos al fragmento Details
     */
    private fun mostrarMovie(movie : Movie) {
        val bundle = Bundle()
        bundle.putInt("MOVIE_ID",movie.id)
        findNavController().navigate(
            R.id.action_popularesFragment_to_detailsFragment,
            bundle
        )
    }
}