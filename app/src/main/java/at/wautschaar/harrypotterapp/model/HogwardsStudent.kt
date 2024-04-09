package at.wautschaar.harrypotterapp.model

import kotlinx.serialization.Serializable

@Serializable
data class HogwardsStudent(
    val id : String,
    val name : String,
    val image : String
)
