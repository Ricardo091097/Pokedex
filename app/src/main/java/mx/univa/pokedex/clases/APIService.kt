package mx.univa.pokedex.clases

import mx.univa.pokedex.model.pokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun consultaPokemon(@Url url:String): Call<pokemonResponse>

}