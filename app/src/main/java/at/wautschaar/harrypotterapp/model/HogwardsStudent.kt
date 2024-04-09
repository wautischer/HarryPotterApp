package at.wautschaar.harrypotterapp.model

import kotlinx.serialization.Serializable

@Serializable
data class HogwardsStudent(
    val id : String,
    val name : String,
    val image : String,
    val species : String,
    val house : String,
    val wizard : Boolean
)
