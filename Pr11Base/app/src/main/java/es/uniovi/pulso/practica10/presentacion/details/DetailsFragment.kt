package es.uniovi.pulso.practica10.presentacion.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.presentacion.MainActivity
import es.uniovi.pulso.practica10.presentacion.MoviesViewModel


class DetailsFragment : Fragment() {
    lateinit var viewModel : MoviesViewModel

    lateinit var navView: BottomNavigationView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = (activity as MainActivity).viewModel

        val root = inflater.inflate(R.layout.fragment_details, container, false)
        navView = root.findViewById(R.id.nav_view)
        cargarMenu()

        val bundle = arguments
        var movieId = -1
        if (bundle != null) {
            movieId = arguments!!.getInt("MOVIE_ID", movieId)
        }

        viewModel.getMovieInfo(movieId)

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            mostrarInfo()
        })

        return root
    }



    private fun mostrarInfo() {
        val infoFragment = InfoFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, infoFragment)
            .commit()
    }

    private fun mostrarReparto() {
        val repartoFragment = RepartoFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, repartoFragment)
            .commit()
    }

    private fun mostrarArgumento() {
        val argumentoFragment = ArgumentoFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, argumentoFragment)
            .commit()
    }

    private fun cargarMenu() {
        //Esto es el listener. Recuerda, el when es similar al switch.
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_argumento -> {
                    mostrarArgumento()
                }
                R.id.navigation_info -> {
                    mostrarInfo()
                }
                R.id.navigation_reparto -> {
                    mostrarReparto()
                }
            }
            true
        }
    }

}