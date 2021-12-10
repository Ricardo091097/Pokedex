package mx.univa.pokedex.clases

import mx.univa.pokedex.model.pokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIServiceCorrutinas {
    @GET
    suspend fun consultaPokemon (@Url url:String): Response<pokemonResponse>
}