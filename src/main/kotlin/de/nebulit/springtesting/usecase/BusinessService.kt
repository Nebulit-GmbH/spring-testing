package de.nebulit.springtesting.usecase

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class BusinessService(@Value("\${endpoint.uri}") var endpointUrl: String, var restTemplate: RestTemplate) {

    private var logger = KotlinLogging.logger{}

    @Retryable(maxAttempts = 3)
    fun businessLogic(data: String) {
        restTemplate.postForEntity(endpointUrl, data, Unit::class.java)
    }

    @Recover
    fun processError(error:Throwable, data: String) {
        logger.error(error) { "Could not process" }
    }
}
