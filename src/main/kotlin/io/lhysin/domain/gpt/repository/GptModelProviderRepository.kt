package io.lhysin.domain.gpt.repository

import io.lhysin.domain.gpt.model.entity.GptModelProviderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GptModelProviderRepository : JpaRepository<GptModelProviderEntity, Long> {

    fun findByModelAndProvider(model: String, provider: String): GptModelProviderEntity?
}