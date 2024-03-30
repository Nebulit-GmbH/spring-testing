package de.nebulit.springtesting.usecase

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import com.github.tomakehurst.wiremock.matching.UrlPattern
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock
import com.maciejwalkowiak.wiremock.spring.EnableWireMock
import com.maciejwalkowiak.wiremock.spring.InjectWireMock
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.kafka.core.KafkaTemplate
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.awaitility.Awaitility
import org.testcontainers.utility.DockerImageName
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

@SpringBootTest
@Testcontainers
@EnableWireMock(ConfigureWireMock(name = "endpoint", property = "endpoint.uri"))
class UseCaseTest {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @InjectWireMock("endpoint")
    private lateinit var wireMockServer: WireMockServer

    @BeforeEach
    fun beforeEach() {
        wireMockServer.stubFor(WireMock.post("/").willReturn(WireMock.status(200)))
    }

    @Test
    fun `use case test`(){

        //given
        kafkaTemplate.send("test-topic", "test-objekt")
        //Awaitility.await().atMost(Duration.ofMinutes(10)).untilTrue(AtomicBoolean(false))
        Awaitility.await().atMost(Duration.ofSeconds(15)).untilAsserted {
            wireMockServer.verify(1, WireMock.postRequestedFor(WireMock.urlEqualTo("/")))
        }
    //when
        // business logic
        //then
        // call http
    }

    companion object {
        @Container
        @ServiceConnection
        private val kafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
    }
}
