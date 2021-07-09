package io.github.aplotnikov.spock_vs_kotest.entities

import io.kotest.assertions.asClue
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import java.math.BigDecimal.ONE
import java.math.BigDecimal.TEN

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ClientTest {

    private val clientFirstName = "Andrii"

    private val clientSecondName = "Plotnikov"

    private val client = Client(clientFirstName, clientSecondName, listOf("test@gmail.com", "test2@gmail.com"))

    companion object {
        @BeforeAll
        @JvmStatic
        fun setUpClass() {
            println("Main class is prepared")
        }

        @AfterAll
        @JvmStatic
        fun tearDownClass() {
            println("Main class is cleaned")
        }
    }

    @BeforeEach
    fun setUpTest() {
        println("Test into ${javaClass.simpleName} class is prepared")
    }

    @AfterEach
    fun tearDownTest() {
        println("Test into ${javaClass.simpleName} class is cleaned")
    }

    @Order(0)
    @Test
    fun `client should have correct first name and second name`() {
        client.asClue {
            it.firstName shouldBe clientFirstName + "1"
            it.secondName shouldBe clientSecondName
        }
    }

    @Order(1)
    @Test
    fun `client should have correct first name and second name - verify all`() {
        assertSoftly(client) {
            firstName shouldBe clientFirstName
            secondName shouldBe clientSecondName
        }
    }

    @Order(2)
    @Disabled
    @Test
    fun `test should be ignored`() {
        throw IllegalStateException("This test should be not launched")
    }

    @Order(3)
    @Test
    fun `client should have correct e-mail addresses`() {
        client.emails shouldContainExactly listOf("test@gmail.com", "test2@gmail.com")
    }

    @Order(4)
    @Test
    fun `client should have status unknown`() {
        client.isUnknown.shouldBeTrue()
    }

    @Order(5)
    @Test
    fun `client should be not able to take a loan when he has unknown status`() {
        // when
        val exception = shouldThrow<IllegalStateException> {
            client.takeLoan(TEN)
        }
        // then
        exception.shouldHaveMessage("In order to take a loan client should have status identified. Current status is UNKNOWN")
    }

    @Order(6)
    @Test
    fun `client should have status registered when registration is completed`() {
        // when
        client.register()
        // then
        client.isRegistered.shouldBeTrue()
    }

    @Order(7)
    @Test
    fun `client should be not able to take a loan when he has registered status`() {
        // when
        val exception = shouldThrow<IllegalStateException> {
            client.takeLoan(TEN)
        }
        // then
        exception.shouldHaveMessage("In order to take a loan client should have status identified. Current status is REGISTERED")
    }

    @Order(8)
    @Test
    fun `client should have status identified when identification is completed`() {
        // when
        client.identify()
        // then
        client.isIdentified.shouldBeTrue()
    }

    @Order(9)
    @Test
    fun `client should not have enough money to take a loan`() {
        // when
        val exception = shouldThrow<IllegalStateException> {
            client.takeLoan(TEN)
        }
        // then
        exception.shouldHaveMessage("Client does not have enough money")
    }

    @Order(10)
    @Test
    fun `client should have enough money to take a loan`() {
        shouldNotThrow<IllegalStateException> {
            client.takeLoan(ONE)
        }
    }
}
