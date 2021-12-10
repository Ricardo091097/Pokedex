package mx.univa.pokedex.clases

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import mx.univa.pokedex.fragments.BlankFragment
import mx.univa.pokedex.fragments.InfoFragment
import mx.univa.pokedex.fragments.MovesFragment
import mx.univa.pokedex.fragments.StatsFragment
import mx.univa.pokedex.model.pokemonResponse

class InfoPagerAdapter (fm: FragmentManager, lifecycle: Lifecycle, datNum: ArrayList<Int>?, datType: ArrayList<String>?, datStats: ArrayList<Int>?, datMoves: ArrayList<String>?) : FragmentStateAdapter(fm, lifecycle) {

    val datNum = datNum
    val datType = datType
    val datStats = datStats
    val datMoves = datMoves

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment

        when(position){
            0 -> {
                var bundle = Bundle()
                bundle.putIntegerArrayList("datNum", datNum)
                bundle.putStringArrayList("datType", datType)
                fragment = InfoFragment()
                fragment.arguments = bundle
            }
            1 -> {
                var bundle = Bundle()
                bundle.putIntegerArrayList("datStat", datStats)
                fragment = StatsFragment()
                fragment.arguments = bundle
            }
            2 -> {
                var bundle = Bundle()
                bundle.putStringArrayList("datMove", datMoves)
                fragment = MovesFragment()
                fragment.arguments = bundle
            }
            else ->{
                fragment = BlankFragment()
            }
        }

        return fragment
    }
}