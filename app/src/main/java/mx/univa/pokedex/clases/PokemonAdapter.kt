package mx.univa.pokedex.clases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.univa.pokedex.R
import mx.univa.pokedex.databinding.ItemPokemonBinding


class PokemonAdapter (val images:List<String>, val nombre: List<String>):RecyclerView.Adapter<PokemonAdapter.ViewHolder>()

{

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener?){
        if (listener != null) {
            mListener = listener
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item!!, nombre[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):ViewHolder{

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_pokemon,parent,false), mListener)
    }

    override fun getItemCount():  Int{
        return images.size
    }


    class ViewHolder(view: View, listener: onItemClickListener): RecyclerView.ViewHolder(view){

        val binding = ItemPokemonBinding.bind(view)
        fun bind (image:String, nombre:String){
            binding.ivPokemon.fromUrl(image)
            binding.txtNombrePokemon.setText(nombre)

        }

        init {
            view.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }

    }


}