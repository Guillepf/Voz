package es.uniovi.pulso.practica10.presentacion.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import coil.load
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.presentacion.MainActivity
import es.uniovi.pulso.practica10.presentacion.MoviesViewModel


class InfoFragment : Fragment() {


    companion object {
        const val MOVIE_FECHA_ESTRENO = "FECHA_ESTRENO"
        const val MOVIE_DURACION = "DURACION"
        const val MOVIE_CARATULA = "CARATULA"


        @JvmStatic
        fun newInstance(fechaEstreno: String, duracion: String, caratula : String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_FECHA_ESTRENO, fechaEstreno)
                    putString(MOVIE_DURACION, duracion)
                    putString(MOVIE_CARATULA, caratula)
                }
            }
    }

    lateinit var viewModel : MoviesViewModel

    private lateinit var tvEstreno : TextView
    private lateinit var tvDuracion : TextView
    private lateinit var ivCaratula : ImageView

    private lateinit var botonAddToFav : Button
    private lateinit var botonDelFromFav : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        val root = inflater.inflate(R.layout.fragment_movie_info, container, false)

        root.let {
            tvEstreno = it.findViewById(R.id.estreno)
            tvDuracion = it.findViewById(R.id.duracion)
            ivCaratula = it.findViewById(R.id.caratula)
            botonAddToFav = it.findViewById(R.id.button_movie_add_fav)
            botonDelFromFav = it.findViewById(R.id.button_movie_delete_fav)
        }

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            tvEstreno.text = it.fechaEstreno
            ivCaratula.load(it.urlCaratula)
            checkFavs(it.favorita)
        })


        botonAddToFav.setOnClickListener {
            viewModel.addToFavorites()
            botonAddToFav.isVisible = false
            botonDelFromFav.isVisible = true
        }
        botonDelFromFav.setOnClickListener {
            viewModel.removeFromFavorites()
            botonAddToFav.isVisible = true
            botonDelFromFav.isVisible = false
        }
        return root
    }

    fun checkFavs(esFavorita : Boolean) {
        botonAddToFav.isVisible = !esFavorita
        botonDelFromFav.isVisible = esFavorita
    }


}