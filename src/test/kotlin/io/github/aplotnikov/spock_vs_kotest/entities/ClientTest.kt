package io.github.aplotnikov.spock_vs_kotest.entities

import io.kotest.assertions.asClue
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.math.BigDecimal.ONE
import java.math.BigDecimal.TEN

class ClientTest {

    private val clientFirstName = "Andrii"

    private val clientSecondName = "Plotnikov"

    private var client = Client(clientFirstName, clientSecondName, listOf("test@gmail.com", "test2@gmail.com"))

    @BeforeAll
    fun setUpClass() {
        println("Main class is prepared")
    }

    @AfterAll
    fun tearDownClass() {
        println("Main class is cleaned")
    }

    @BeforeEach
    fun setUpTest() {
        println("Test into ${javaClass.simpleName} class is prepared")
    }

    @AfterEach
    fun tearDownTest() {
        println("Test into ${javaClass.simpleName} class is cleaned")
    }

    @Test
    fun `client should have correct first name and second name`() {
        client.asClue {
            it.firstName shouldBe clientFirstName
            it.secondName shouldBe clientSecondName
        }
    }

    @Test
    fun `client should have correct first name and second name - verify all`() {
        assertSoftly(client) {
            firstName shouldBe clientFirstName
            secondName shouldBe clientSecondName
        }
    }

    @Disabled
    @Test
    fun `test should be ignored`() {
        throw IllegalStateException("This test should be not launched")
    }

    @Test
    fun `client should have correct e-mail addresses`() {
        client.emails shouldContainExactly listOf("test@gmail.com", "test2@gmail.com")
    }

    @Test
    fun `client should have status unknown`() {
        client.isUnknown shouldBe true
    }

    @Test
    fun `client should be not able to take a loan when he has unknown status`() {
        // when
        val exception = shouldThrow<IllegalStateException> {
            client.takeLoan(TEN)
        }
        // then
        exception.message shouldBe "In order to take a lona client should have status identified. Current status is UNKNOWN"
    }

    @Test
    fun `client should have status registered when registration is completed`() {
        // when
        client.register()
        // then
        client.isRegistered shouldBe true
    }

    @Test
    fun `client should not have enough money to take a loan`() {
        // when
        val exception = shouldThrow<IllegalStateException> {
            client.takeLoan(TEN)
        }
        // then
        exception.message shouldBe "Client does not have enough money"
    }

    @Test
    fun `client should have enough money to take a loan`() {
        shouldNotThrow<IllegalStateException> {
            client.takeLoan(ONE)
        }
    }
}
