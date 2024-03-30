package de.nebulit.springtesting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.retry.annotation.EnableRetry
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableKafka
@EnableRetry
class SpringTestingApplication {
	@Bean
	fun restTemplate():RestTemplate {
		return RestTemplate()
	}
}

fun main(args: Array<String>) {
	runApplication<SpringTestingApplication>(*args)
}
