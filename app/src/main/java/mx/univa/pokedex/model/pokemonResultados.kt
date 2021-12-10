package mx.univa.pokedex.model

data class pokemonResultados(
    var next: String,
    var previous: String,
    var results: ArrayList<pokemonResult>
)
