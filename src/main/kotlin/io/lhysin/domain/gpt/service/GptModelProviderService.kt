package io.lhysin.domain.gpt.service

import io.lhysin.domain.gpt.model.entity.GptModelProviderEntity
import io.lhysin.domain.gpt.repository.GptModelProviderRepository
import org.springframework.stereotype.Service

@Service
class GptModelProviderService (
    private val gptModelProviderRepository: GptModelProviderRepository,
){
    fun findOrCreateGptModelProvider(model: String, provider: String): GptModelProviderEntity {
        return gptModelProviderRepository.findByModelAndProvider(model, provider)
            ?: gptModelProviderRepository.save(GptModelProviderEntity(model = model, provider = provider))
    }
}