package io.github.aplotnikov.spock_vs_kotest.entities

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.framework.concurrency.eventually
import io.kotest.matchers.booleans.shouldBeTrue
import java.math.BigDecimal.TEN
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalKotest
class SlowClientKoTest : ShouldSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    private val clientFirstName = "Andrii"

    private val clientSecondName = "Plotnikov"

    private val client = Client(clientFirstName, clientSecondName, listOf("test@gmail.com", "test2@gmail.com"))

    init {
        should("client pay in max 2 seconds").config(timeout = seconds(2)) {
            client.pay(TEN)
        }

        should("client pay identification fee in max 2 second with async conditions") {
            startPaymentOfIdentificationFee()
            eventually(seconds(2)) {
                client.isIdentified.shouldBeTrue()
            }
        }
    }

    private fun startPaymentOfIdentificationFee() {
        val executorService: ExecutorService = newSingleThreadScheduledExecutor()
        try {
            executorService.submit { client.payIdentificationFee() }
        } finally {
            executorService.shutdown()
        }
    }
}
