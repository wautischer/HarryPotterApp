package at.wautschaar.harrypotterapp.network

import at.wautschaar.harrypotterapp.model.HogwardsStudent
import at.wautschaar.harrypotterapp.model.RoomMates
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import retrofit2.http.GET

private const val BASE_URL = "https://hp-api.onrender.com/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())
    ).baseUrl(BASE_URL).build()

interface HPAPIService {
    @GET("characters/students")
    suspend fun getStudents(): List<HogwardsStudent>

    @GET("characters/house/gryffindor")
    suspend fun getRoomMatesGryffindor(): List<RoomMates>
    @GET("characters/house/hufflepuff")
    suspend fun getRoomMatesHufflepuff(): List<RoomMates>

    @GET("characters/house/ravenclaw")
    suspend fun getRoomMatesRavenclaw(): List<RoomMates>

    @GET("characters/house/slytherin")
    suspend fun getRoomMatesSlitherin(): List<RoomMates>

}


object HPAPI {
    val retrofitService: HPAPIService by lazy {
        retrofit.create(HPAPIService::class.java)
    }
}