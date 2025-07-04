package br.com.ricarlo.home.data.repository

import br.com.ricarlo.home.data.remote.ApiHome
import br.com.ricarlo.home.data.remote.FruitResponse
import br.com.ricarlo.home.domain.repository.HomeRepository

internal class HomeRepositoryImpl(
    private val apiHome: ApiHome
) : HomeRepository {
    override suspend fun getFruits(): List<FruitResponse> {
        return apiHome.getFruits()
    }
}
