package mx.univa.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import mx.univa.pokedex.clases.InfoPagerAdapter
import mx.univa.pokedex.clases.PokemonAdapter
import mx.univa.pokedex.clases.PokemonPagerAdapter
import mx.univa.pokedex.databinding.ActivityDatosPokemonBinding
import mx.univa.pokedex.model.pokemonResponse
import java.util.ArrayList

class DatosPokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatosPokemonBinding
    private lateinit var datosPokemonAdapter: PokemonPagerAdapter
    private lateinit var datosPokemonAdapter2: InfoPagerAdapter
    private var shiny: Boolean = false
    private var gender: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatosPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noPoke = intent.getIntExtra("id", 0)
        if(noPoke<10){
            binding.txtNoPokemon.setText("No.00"+noPoke.toString())
        }else{
            if(noPoke<100){
                binding.txtNoPokemon.setText("No.0"+noPoke.toString())
            }else{
                binding.txtNoPokemon.setText("No."+noPoke.toString())
            }
        }

        binding.txtNombrePokemon.setText(intent.getStringExtra("pokemonName").toString().uppercase())

        var images = listOf(
            intent.getStringArrayListExtra("pokemonListaImagen")?.get(0),
            intent.getStringArrayListExtra("pokemonListaImagen")?.get(1)
        )

        datosPokemonAdapter = PokemonPagerAdapter(images as List<String>)
        binding.pager.adapter = datosPokemonAdapter

        binding.btnShiny.setOnClickListener {
            if(!shiny){
                images = listOf(
                    intent.getStringArrayListExtra("pokemonListaImagen")?.get(2),
                    intent.getStringArrayListExtra("pokemonListaImagen")?.get(3)
                )
                datosPokemonAdapter = PokemonPagerAdapter(images as List<String>)
                binding.pager.adapter = datosPokemonAdapter
                binding.btnShiny.setImageResource(R.drawable.ic_stars_svgrepo_com_2)
                shiny = true
            }else{
                images = listOf(
                    intent.getStringArrayListExtra("pokemonListaImagen")?.get(0),
                    intent.getStringArrayListExtra("pokemonListaImagen")?.get(1)
                )
                datosPokemonAdapter = PokemonPagerAdapter(images as List<String>)
                binding.pager.adapter = datosPokemonAdapter
                binding.btnShiny.setImageResource(R.drawable.ic_stars_svgrepo_com)
                shiny = false
            }
        }

        binding.btnGender.setOnClickListener {
            if(!gender){
                binding.btnGender.setImageResource(R.drawable.ic_femenine)
                gender = true
            }else{
                binding.btnGender.setImageResource(R.drawable.ic_masculine)
                gender = false
            }
        }

        var datNum: ArrayList<Int>? = intent.getIntegerArrayListExtra("datNum")
        var datType: ArrayList<String>? = intent.getStringArrayListExtra("datType")
        var datStats: ArrayList<Int>? = intent.getIntegerArrayListExtra("datStat")
        var datMoves: ArrayList<String>? = intent.getStringArrayListExtra("datMove")



        datosPokemonAdapter2 = InfoPagerAdapter(supportFragmentManager, lifecycle, datNum, datType, datStats, datMoves)
        binding.pager2.adapter = datosPokemonAdapter2

        val tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, binding.pager2){tab, position->
            when(position){
                0 -> {
                    tab.text = "Info"
                    tab.setIcon(R.drawable.ic_baseline_info_24)
                }
                1 -> {
                    tab.text = "Stats"
                    tab.setIcon(R.drawable.ic_outline_upgrade_24)
                }
                2 -> {
                    tab.text = "Moves"
                    tab.setIcon(R.drawable.ic_baseline_local_fire_department_24)
                }
            }
        }.attach()


    }


}