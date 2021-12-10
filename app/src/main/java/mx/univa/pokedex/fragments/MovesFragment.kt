package mx.univa.pokedex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.univa.pokedex.R
import mx.univa.pokedex.clases.MovesAdapter
import mx.univa.pokedex.clases.PokemonAdapter

private const val ARG_PARAM1 = "param1"


class MovesFragment : Fragment() {
    private var param1: ArrayList<String>? = null
    private lateinit var movesAdapter: MovesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getStringArrayList("datMove")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_moves, container, false)
        movesAdapter = MovesAdapter(param1!!)
        val rvMoves : RecyclerView = v.findViewById(R.id.rvMoves)
        rvMoves.setHasFixedSize(true)
        rvMoves.layoutManager = LinearLayoutManager(container?.context)
        rvMoves.adapter = movesAdapter

        return v
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MovesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}