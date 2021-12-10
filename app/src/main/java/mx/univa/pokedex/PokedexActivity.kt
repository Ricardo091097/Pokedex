package mx.univa.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.univa.pokedex.clases.APIService

import mx.univa.pokedex.clases.PokemonAdapter
import mx.univa.pokedex.databinding.ActivityPokedexBinding
import mx.univa.pokedex.model.pokemonResponse
import mx.univa.pokedex.model.typesResponse
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.yesButton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class PokedexActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityPokedexBinding
    private lateinit var pokemonAdapter: PokemonAdapter
    private var imagesPokemon:ArrayList<String> = ArrayList()
    private var namesPokemon:ArrayList<String> = ArrayList()
    private var listPokemon:ArrayList<pokemonResponse> = ArrayList()
    private var listoParaCargar:Boolean = true;
    private var respuesta:Boolean = true
    var pos: Int = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buscarPokemon2(1, false)
        binding.buscarPokemon.setOnQueryTextListener(this)
        binding.rvPokemon.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy>0&&listoParaCargar){
                    val layoutManager = binding.rvPokemon.layoutManager as LinearLayoutManager
                    val lastVisible: Int = layoutManager.findLastCompletelyVisibleItemPosition()
                    if(lastVisible==pos&&lastVisible!=897){
                        buscarPokemon2(pos+2, true)
                        pos += 21
                    }else{
                        if(lastVisible==898){
                            showErrorDialog()
                        }
                    }
                }
            }
        })
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun buscarPorTexto(busqueda:String){
        listoParaCargar = false
        if(!busqueda.equals("")){
            doAsync{
                val call=
                    getRetrofit().create(APIService::class.java).consultaPokemon("pokemon/$busqueda")
                        .execute()

                var pokemon : pokemonResponse? = null

                if(call.code()==404){
                    respuesta = false
                }else{
                    respuesta = true
                    pokemon = call.body() as pokemonResponse

                }

                uiThread {
                    if(respuesta){
                        initRecycler2(pokemon!!)
                    }else{
                        showErrorDialog()
                        listoParaCargar = true
                    }
                    hideKeyboard()
                }

            }
        }

    }

    private fun buscarPokemon2(id: Int, cargar: Boolean){

        var suma: Int = 21;

        if((898-id)<21){
            suma = 898-id;
        }else{
            doAsync {
                var call2 = getRetrofit().create(APIService::class.java).consultaPokemon("pokemon/$id").execute()
                var pokemon2 = call2.body() as pokemonResponse
                for (i in id until id+suma){
                    call2 = getRetrofit().create(APIService::class.java).consultaPokemon("pokemon/$i").execute()
                    pokemon2 = call2.body() as pokemonResponse
                    listPokemon.add(pokemon2)
                }
                uiThread {
                    if(pokemon2.name!=""){
                        initRecycler(listPokemon, listPokemon.size, cargar)
                    }else{
                        showErrorDialog()
                    }
                }
            }
        }

    }


    private fun showErrorDialog(){

        alert("Ha ocurrido un error, intentelo de nuevo"){
            yesButton{ }
        }.show()
    }

    private fun initRecycler2(pokemon: pokemonResponse){
        if(pokemon.name !=""){
            imagesPokemon.clear()
            namesPokemon.clear()
            listPokemon.clear()
            listPokemon.add(pokemon!!)
            imagesPokemon.add(pokemon.sprites.front_default!!)
            namesPokemon.add(pokemon.name)
            pokemonAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecycler(pokemon:ArrayList<pokemonResponse>, tam: Int, cargar: Boolean){
        if(pokemon[0].name !=""){
            for (i in tam-21 until tam){
                imagesPokemon.add(pokemon[i].sprites.front_default!!)
                namesPokemon.add(pokemon[i].name)
            }
            if (cargar){
                pokemonAdapter.notifyDataSetChanged()
            }else{
                pokemonAdapter = PokemonAdapter(imagesPokemon, namesPokemon)
                binding.rvPokemon.setHasFixedSize(true)
                binding.rvPokemon.layoutManager = GridLayoutManager(this, 3)
                binding.rvPokemon.adapter = pokemonAdapter
            }

            pokemonAdapter.setOnItemClickListener(object: PokemonAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    var intento = Intent(this@PokedexActivity, DatosPokemonActivity::class.java)

                    if(listPokemon.size==1){
                        var img: ArrayList<String> = ArrayList()
                        img.add(listPokemon[position].sprites.front_default!!)
                        img.add(listPokemon[position].sprites.back_default!!)
                        img.add(listPokemon[position].sprites.front_shiny!!)
                        img.add(listPokemon[position].sprites.back_shiny!!)

                        var datNum: ArrayList<Int> = ArrayList()
                        datNum.add(listPokemon[position].height)
                        datNum.add(listPokemon[position].weight)
                        datNum.add(listPokemon[position].base_experience)

                        var datType: ArrayList<String> = ArrayList()
                        val types: Array<typesResponse> = listPokemon[position].types
                        types.forEach {
                            datType.add(it.type.name)
                        }

                        var datStats : ArrayList<Int> = ArrayList()
                        val stats = listPokemon[position].stats
                        stats.forEach {
                            datStats.add(it.base_stat)
                        }

                        var datMoves : ArrayList<String> = ArrayList()
                        val moves = listPokemon[position].moves

                        moves.forEach {
                            datMoves.add(it.move.name)
                        }

                        intento.putExtra("pokemonName", listPokemon[position].name)
                        intento.putStringArrayListExtra("pokemonListaImagen", img)
                        intento.putExtra("id", listPokemon[position].id)
                        intento.putIntegerArrayListExtra("datNum", datNum)
                        intento.putStringArrayListExtra("datType", datType)
                        intento.putIntegerArrayListExtra("datStat", datStats)
                        intento.putStringArrayListExtra("datMove", datMoves)


                        startActivity(intento)
                    }else{
                        var img: ArrayList<String> = ArrayList()
                        img.add(pokemon[position].sprites.front_default!!)
                        img.add(pokemon[position].sprites.back_default!!)
                        img.add(pokemon[position].sprites.front_shiny!!)
                        img.add(pokemon[position].sprites.back_shiny!!)

                        var datNum: ArrayList<Int> = ArrayList()
                        datNum.add(pokemon[position].height)
                        datNum.add(pokemon[position].weight)
                        datNum.add(pokemon[position].base_experience)

                        var datType: ArrayList<String> = ArrayList()
                        val types: Array<typesResponse> = pokemon[position].types

                        types.forEach {
                            datType.add(it.type.name)
                        }

                        var datStats : ArrayList<Int> = ArrayList()
                        val stats = pokemon[position].stats
                        stats.forEach {
                            datStats.add(it.base_stat)
                        }

                        var datMoves : ArrayList<String> = ArrayList()
                        val moves = pokemon[position].moves

                        moves.forEach {
                            datMoves.add(it.move.name)
                        }

                        intento.putExtra("pokemonName", pokemon[position].name)
                        intento.putStringArrayListExtra("pokemonListaImagen", img)
                        intento.putExtra("id", pokemon[position].id)
                        intento.putIntegerArrayListExtra("datNum", datNum)
                        intento.putStringArrayListExtra("datType", datType)
                        intento.putIntegerArrayListExtra("datStat", datStats)
                        intento.putStringArrayListExtra("datMove", datMoves)

                        startActivity(intento)
                    }


                }

            })

        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        buscarPorTexto(p0!!.lowercase(Locale.getDefault()))
        return true
    }

    override fun onQueryTextChange(p0:String?):Boolean{
        if(p0.equals("")&&respuesta==true){
            imagesPokemon.clear()
            namesPokemon.clear()
            listPokemon.clear()
            buscarPokemon2(1, false)
            listoParaCargar = true
            pos = 20
        }
        return true
    }

    private fun hideKeyboard(){

        val imm=getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRootRetrofit.windowToken,0)

    }


}