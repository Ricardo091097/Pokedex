package mx.univa.pokedex.model

data class pokemonResponse(
    val base_experience:Int,
    val height:Int,
    val id:Int,
    val is_default:Boolean,
    val location_area_encounters:String,
    val name:String,
    val order:Int,
    val weight:Int,
    val sprites:spritesResponse,
    val types: Array<typesResponse>,
    val stats: Array<statsResponse>,
    val moves: Array<movesResponse>
    )
