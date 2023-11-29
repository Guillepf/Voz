package es.uniovi.pulso.practica10.presentacion.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.presentacion.MainActivity
import es.uniovi.pulso.practica10.presentacion.MoviesViewModel
import es.uniovi.pulso.practica10.presentacion.adapters.RepartoAdapter


class RepartoFragment : Fragment() {

    companion object {
        const val MOVIE_ID = "MOVIE_ID"

        @JvmStatic
        fun newInstance(movieId: Int) =
            RepartoFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)
                }
            }
    }

    lateinit var viewModel : MoviesViewModel

    private lateinit var recylerInterpretes : RecyclerView
    private lateinit var repartoAdapter : RepartoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("PR11", "Generar vista Reparto")
        viewModel = (activity as MainActivity).viewModel
        viewModel.getMovieCredits()

        val root = inflater.inflate(R.layout.fragment_movie_reparto, container, false)

        root.let {
            recylerInterpretes = it.findViewById(R.id.reciclerViewReparto)
        }

        recylerInterpretes.layoutManager = LinearLayoutManager(context)

        recylerInterpretes.setHasFixedSize(true)
        repartoAdapter = RepartoAdapter()
        recylerInterpretes.adapter = repartoAdapter

        viewModel.movieCredits.observe(viewLifecycleOwner, Observer {
            repartoAdapter.update(it)
        })

        return root

    }
}