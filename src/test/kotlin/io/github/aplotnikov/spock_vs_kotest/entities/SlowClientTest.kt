package io.github.aplotnikov.spock_vs_kotest.entities

import io.kotest.matchers.shouldBe
import org.awaitility.Awaitility.await
import org.awaitility.Durations.TWO_SECONDS
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import org.junit.jupiter.api.assertTimeoutPreemptively
import java.math.BigDecimal.TEN
import java.time.Duration.ofSeconds
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.TimeUnit.SECONDS

class SlowClientTest {

    private val clientFirstName = "Andrii"

    private val clientSecondName = "Plotnikov"

    private val client = Client(clientFirstName, clientSecondName, listOf("test@gmail.com", "test2@gmail.com"))

    @Test
    fun `should pay in max 2 seconds`() {
        assertTimeout(ofSeconds(2)) {
            client.pay(TEN)
        }
    }

    @Test
    fun `should pay in max 2 seconds - 2`() {
        assertTimeoutPreemptively(ofSeconds(2)) {
            client.pay(TEN)
        }
    }

    @Test
    fun `should pay identification fee in max 2 seconds - assertTimeout`() {
        assertTimeout(ofSeconds(2)) {
            client.payIdentificationFee()
        }
        client.isIdentified shouldBe true
    }

    @Test
    fun `should pay identification fee in max 2 seconds - assertTimeoutPreemptively`() {
        assertTimeoutPreemptively(ofSeconds(2)) {
            client.payIdentificationFee()
        }
        client.isIdentified shouldBe true
    }

    @Test
    @Throws(Exception::class)
    fun `should pay identification fee in max 2 seconds - executor service`() {
        val executorService: ExecutorService = newSingleThreadScheduledExecutor()
        try {
            val result = executorService.submit {
                client.payIdentificationFee()
            }
            result.get(2, SECONDS)

            client.isIdentified shouldBe true
        } finally {
            executorService.shutdown()
        }
    }

    @Test
    fun `should pay identification fee in max 2 seconds - awaitility`() {
        startPaymentOfIdentificationFee {
            await().atMost(TWO_SECONDS).untilAsserted {
                client.isIdentified shouldBe true
            }
        }
    }

    @Test
    fun `should pay identification fee in max 2 seconds - awaitility 2`() {
        startPaymentOfIdentificationFee {
            await().atMost(TWO_SECONDS).until {
                client.isIdentified
            }
        }
    }

    private fun startPaymentOfIdentificationFee(operation: Runnable) {
        val executorService: ExecutorService = newSingleThreadScheduledExecutor()
        try {
            executorService.run {
                submit {
                    client.payIdentificationFee()
                }
                submit(operation)
            }
        } finally {
            executorService.shutdown()
        }
    }
}
