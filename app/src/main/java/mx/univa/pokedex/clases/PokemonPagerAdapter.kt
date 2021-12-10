package mx.univa.pokedex.clases

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.univa.pokedex.R
import mx.univa.pokedex.databinding.ItemPokemonImagesBinding

class PokemonPagerAdapter (val images:List<String>):RecyclerView.Adapter<PokemonPagerAdapter.ViewHolder>() {



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):ViewHolder{

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_pokemon_images,parent,false))
    }

    override fun getItemCount():  Int{
        return images.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val binding = ItemPokemonImagesBinding.bind(view)
        fun bind (image:String){
            binding.ivPokemon.fromUrl(image)
        }


    }


}