package br.com.ricarlo.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Greeting {

    suspend fun greeting(): List<FruitResponse> {
        // https://github.com/public-apis/public-apis?tab=readme-ov-file
        return KtorHttpClient
            .httpClient()
            .get("https://www.fruityvice.com/api/fruit/all")
            .body()
    }
}

@Serializable
data class FruitResponse(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Long,
    @SerialName("family") val family: String,
    @SerialName("order") val order: String,
    @SerialName("genus") val genus: String,
    @SerialName("nutritions") val nutrition: NutritionResponse,
)

@Serializable
data class NutritionResponse(
    @SerialName("calories") val calories: Long,
    @SerialName("fat") val fat: Double,
    @SerialName("sugar") val sugar: Double,
    @SerialName("carbohydrates") val carbohydrates: Double,
    @SerialName("protein") val protein: Double
)
