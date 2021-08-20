package io.github.aplotnikov.spock_vs_kotest.entities

import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.math.BigDecimal.TEN

class ClientContextKoTest : ShouldSpec() {
    private val clientFirstName = "Andrii"

    private val clientSecondName = "Plotnikov"

    private val client = Client(clientFirstName, clientSecondName, listOf("test@gmail.com", "test2@gmail.com"))

    override fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        println("Main class is prepared")
    }

    override fun afterSpec(spec: Spec) {
        super.afterSpec(spec)
        println("Main class is cleaned")
    }

    override fun beforeTest(testCase: TestCase) {
        println("Test into ${javaClass.simpleName} class is prepared")
        super.beforeTest(testCase)
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
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

        should("client has status unknown") {
            client.isUnknown.shouldBeTrue()
        }

        context("When client is created") {
            beforeTest {
                println("Preparation into `When client is created` context")
            }

            afterTest {
                println("Cleanup into `When client is created` context")
            }

            should("client has correct first name and second name") {
                client.asClue {
                    it.firstName shouldBe clientFirstName
                    it.secondName shouldBe clientSecondName
                }
            }

            should("client has status unknown") {
                client.isUnknown.shouldBeTrue()
            }

            context("When client is created - inner context") {
                beforeTest {
                    println("Preparation into `When client is created - inner context` context")
                }

                afterTest {
                    println("Cleanup into `When client is created - inner context` context")
                }

                should("client has correct first name and second name") {
                    client.asClue {
                        it.firstName shouldBe clientFirstName
                        it.secondName shouldBe clientSecondName
                    }
                }

                should("client has status unknown") {
                    client.isUnknown.shouldBeTrue()
                }
            }
        }

        context("When registration is completed") {
            beforeTest {
                println("Preparation into `When registration is completed` context")
                client.register()
            }

            afterTest {
                println("Cleanup into `When registration is completed` context")
            }

            should("client has status registered when registration is completed") {
                client.isRegistered.shouldBeTrue()
            }

            should("client be not able to take a loan when he has registered status") {
                val exception = shouldThrow<IllegalStateException> {
                    client.takeLoan(TEN)
                }
                exception.shouldHaveMessage("In order to take a loan client should have status identified. Current status is REGISTERED")
            }

            context("When identification is completed") {
                beforeTest {
                    println("Preparation into `When identification is completed` context")
                    client.identify()
                }

                afterTest {
                    println("Cleanup into `When identification is completed` context")
                }

                should("client has status identified when identification is completed") {
                    client.isIdentified.shouldBeTrue()
                }

                should("client don't have enough money to take a loan") {
                    val exception = shouldThrow<IllegalStateException> {
                        client.takeLoan(TEN)
                    }
                    exception.shouldHaveMessage("Client does not have enough money")
                }
            }
        }
    }
}
