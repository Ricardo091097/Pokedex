package mx.univa.pokedex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mx.univa.pokedex.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InfoFragment : Fragment() {
    private var param1: ArrayList<Int>? = null
    private var param2: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getIntegerArrayList("datNum")
            param2 = it.getStringArrayList("datType")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_info, container, false)
        val txtPeso : TextView = v.findViewById(R.id.txtPesoPokemon)
        txtPeso.setText((param1?.get(1)?.div(10.0)).toString()+" kg") //Weight
        val txtAltura : TextView = v.findViewById(R.id.txtAlturaPokemon)
        txtAltura.setText((param1?.get(0)?.div(10.0)).toString()+" m") //Height
        val txtTipo1 : TextView = v.findViewById(R.id.txtTipo1)
        val txtTipo2 : TextView = v.findViewById(R.id.txtTipo2)
        val txtTipo3 : TextView = v.findViewById(R.id.txtTipo3)
        val tipos: ArrayList<String> = ArrayList()
        param2?.forEach {
            when(it){
                "normal" -> {
                    tipos.add("Normal")
                }
                "fighting" -> {
                    tipos.add("Pelea")
                }
                "flying" -> {
                    tipos.add("Volador")
                }
                "poison" -> {
                    tipos.add("Veneno")
                }
                "ground" -> {
                    tipos.add("Tierra")
                }
                "rock" -> {
                    tipos.add("Piedra")
                }
                "bug" -> {
                    tipos.add("Insecto")
                }
                "ghost" -> {
                    tipos.add("Fantasma")
                }
                "steel" -> {
                    tipos.add("Acero")
                }
                "fire" -> {
                    tipos.add("Fuego")
                }
                "water" -> {
                    tipos.add("Agua")
                }
                "grass" -> {
                    tipos.add("Hierva")
                }
                "electric" -> {
                    tipos.add("Electrico")
                }
                "psychic" -> {
                    tipos.add("Psiquico")
                }
                "ice" -> {
                    tipos.add("Hielo")
                }
                "dragon" -> {
                    tipos.add("Dragon")
                }
                "dark" -> {
                    tipos.add("Siniestro")
                }
                "fairy" -> {
                    tipos.add("Hada")
                }
                "unknown" -> {
                    tipos.add("???")
                }
                "shadow" -> {
                    tipos.add("Sombra")
                }
            }
        }


        when(tipos.size){
            1 ->{
                txtTipo1.setText(tipos.get(0))
            }
            2 ->{
                txtTipo1.setText(tipos.get(0))
                txtTipo2.setText(tipos.get(1))
            }
            3 ->{
                txtTipo1.setText(tipos.get(0))
                txtTipo2.setText(tipos.get(1))
                txtTipo3.setText(tipos.get(2))
            }
        }




        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}