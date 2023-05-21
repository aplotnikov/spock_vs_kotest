package io.github.aplotnikov.spock.vs.kotest.entities

import io.kotest.assertions.asClue
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestCaseOrder
import io.kotest.core.test.TestResult
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.math.BigDecimal.ONE
import java.math.BigDecimal.TEN

class ClientKoSpec : ShouldSpec() {

    override fun testCaseOrder() = TestCaseOrder.Sequential

    private val clientFirstName = "Andrii"

    private val clientSecondName = "Plotnikov"

    private val client = Client(clientFirstName, clientSecondName, listOf("test@gmail.com", "test2@gmail.com"))

    override suspend fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        println("Main class is prepared")
    }

    override suspend fun afterSpec(spec: Spec) {
        super.afterSpec(spec)
        println("Main class is cleaned")
    }

    override suspend fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        println("Test into ${javaClass.simpleName} class is prepared")
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        println("Test into ${javaClass.simpleName} class is cleaned")
    }

    init {
        should("client has correct first name and second name") {
            client.asClue {
                it.firstName shouldBe clientFirstName
                it.secondName shouldBe clientSecondName
            }
        }

        should("client has correct first name and second name - verify all") {
            assertSoftly(client) {
                firstName shouldBe clientFirstName
                secondName shouldBe clientSecondName
            }
        }

        xshould("test be ignored") {
            throw IllegalStateException("This test should be not launched")
        }

        should("client has correct e-mail addresses") {
            client.emails shouldContainExactly listOf("test@gmail.com", "test2@gmail.com")
        }

        should("client has status unknown") {
            client.isUnknown.shouldBeTrue()
        }

        should("client be not able to take a loan when he has unknown status") {
            // when
            val exception = shouldThrow<IllegalStateException> {
                client.takeLoan(TEN)
            }
            // then
            exception shouldHaveMessage "In order to take a loan client should have status identified. Current status is UNKNOWN"
        }

        should("client has status registered when registration is completed") {
            // when
            client.register()
            // then
            client.isRegistered.shouldBeTrue()
        }

        should("client be not able to take a loan when he has registered status") {
            // when
            val exception = shouldThrow<IllegalStateException> {
                client.takeLoan(TEN)
            }
            // then
            exception shouldHaveMessage "In order to take a loan client should have status identified. Current status is REGISTERED"
        }

        should("client be not able to take a loan when he has registered status 2") {
            shouldThrowWithMessage<IllegalStateException>("In order to take a loan client should have status identified. Current status is REGISTERED") {
                client.takeLoan(TEN)
            }
        }

        should("client has status identified when identification is completed") {
            // when
            client.identify()
            // then
            client.isIdentified.shouldBeTrue()
        }

        should("client don't have enough money to take a loan") {
            // when
            val exception = shouldThrow<IllegalStateException> {
                client.takeLoan(TEN)
            }
            // then
            exception shouldHaveMessage "Client does not have enough money"
        }

        should("client has enough money to take a loan") {
            shouldNotThrow<IllegalStateException> {
                client.takeLoan(ONE)
            }
        }
    }
}
