package es.uniovi.pulso.practica10.presentacion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import es.uniovi.pulso.practica10.R
import es.uniovi.pulso.practica10.datos.modelos.Movie

class MovieAdapter(

    private var listaMovies: List<Movie> = emptyList(),
    private val onItemSelected: (Movie) -> Unit,

    ) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgView = itemView.findViewById<ImageView>(R.id.ivPortada)
        private val tvTitulo = itemView.findViewById<TextView>(R.id.tvTitulo);
        private val rootView = itemView.rootView;


        fun bindView(movie: Movie, onItemSelected : (Movie) -> Unit ) {


            imgView.load(movie.urlFondo) {
                crossfade(true)
                crossfade(500)
            }
            tvTitulo.text = movie.titulo

            //Listener
            rootView.setOnClickListener { onItemSelected(movie) };
        }
    }


    /*
     *  Notificación de cambios.
     */
    fun update(listaMovies : List<Movie>) {
        this.listaMovies = listaMovies
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.reycler_item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int = listaMovies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindView(listaMovies[position],onItemSelected)

}