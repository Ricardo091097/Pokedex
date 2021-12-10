package mx.univa.pokedex.clases


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.univa.pokedex.R
import mx.univa.pokedex.databinding.ItemMovesBinding


class MovesAdapter(val moves: ArrayList<String>) : RecyclerView.Adapter<MovesAdapter.ViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_moves,parent,false))
    }

    override fun onBindViewHolder(holder: MovesAdapter.ViewHolder, position: Int) {
        val item = moves[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return moves.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val binding = ItemMovesBinding.bind(view)
        fun bind (moves:String){
            binding.txtMove.setText(moves)
        }

    }

}