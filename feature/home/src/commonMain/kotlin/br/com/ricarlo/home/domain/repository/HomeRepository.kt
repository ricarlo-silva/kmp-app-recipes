package br.com.ricarlo.home.domain.repository

import br.com.ricarlo.home.data.remote.FruitResponse

interface HomeRepository {
    suspend fun getFruits(): List<FruitResponse>
}
