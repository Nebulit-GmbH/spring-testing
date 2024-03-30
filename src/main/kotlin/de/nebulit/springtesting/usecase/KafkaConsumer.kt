package de.nebulit.springtesting.usecase

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(var businessService: BusinessService) {

    @KafkaListener(topics = ["test-topic"])
    fun consumeRecord(consumerRecord: ConsumerRecord<String, String>) {
        businessService.businessLogic(consumerRecord.value())
    }
}
