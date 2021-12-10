package mx.univa.pokedex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mx.univa.pokedex.R

private const val ARG_PARAM1 = "param1"

class StatsFragment : Fragment() {
    private var param1: ArrayList<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getIntegerArrayList("datStat")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_stats, container, false)

        val txtHP : TextView = v.findViewById(R.id.txtHP)
        txtHP.setText("HP: "+param1?.get(0).toString())
        val txtAttack : TextView = v.findViewById(R.id.txtAttack)
        txtAttack.setText("Ataque :"+param1?.get(1).toString())
        val txtDefense : TextView = v.findViewById(R.id.txtDefense)
        txtDefense.setText("Defensa: "+param1?.get(2).toString())
        val txtSpecialA : TextView = v.findViewById(R.id.txtSpecialA)
        txtSpecialA.setText("Ataque especial: "+param1?.get(3).toString())
        val txtSpecialD : TextView = v.findViewById(R.id.txtSpecialD)
        txtSpecialD.setText("Defensa especial: "+param1?.get(4).toString())
        val txtSpeed : TextView = v.findViewById(R.id.txtSpeed)
        txtSpeed.setText("Velocidad: "+param1?.get(5).toString())

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}