package io.github.aplotnikov.spock_vs_kotest.entities

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.SoftAssertions
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
        client.run {
            assertThat(firstName).isEqualTo(clientFirstName)
            assertThat(secondName).isEqualTo(clientSecondName)
        }
    }

    @Order(1)
    @Test
    fun `client should have correct first name and second name - verify all`() {
        SoftAssertions().also {
            client.run {
                it.assertThat(firstName).isEqualTo(clientFirstName)
                it.assertThat(secondName).isEqualTo(clientSecondName)
            }
        }.assertAll()
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
        assertThat(client.emails).containsExactlyElementsOf(listOf("test@gmail.com", "test2@gmail.com"))
    }

    @Order(4)
    @Test
    fun `client should have status unknown`() {
        assertThat(client.isUnknown).isTrue
    }

    @Order(5)
    @Test
    fun `client should be not able to take a loan when he has unknown status`() {
        assertThatThrownBy { client.takeLoan(TEN) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage(
                "In order to take a loan client should have status identified. Current status is UNKNOWN"
            )
    }

    @Order(6)
    @Test
    fun `client should have status registered when registration is completed`() {
        // when
        client.register()
        // then
        assertThat(client.isRegistered).isTrue
    }

    @Order(7)
    @Test
    fun `client should be not able to take a loan when he has registered status`() {
        assertThatThrownBy { client.takeLoan(TEN) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage(
                "In order to take a loan client should have status identified. Current status is REGISTERED"
            )
    }

    @Order(8)
    @Test
    fun `client should have status identified when identification is completed`() {
        // when
        client.identify()
        // then
        assertThat(client.isIdentified).isTrue
    }

    @Order(9)
    @Test
    fun `client should not have enough money to take a loan`() {
        assertThatThrownBy {
            client.takeLoan(TEN)
        }.hasMessage(
            "Client does not have enough money"
        )
    }

    @Order(10)
    @Test
    fun `client should have enough money to take a loan`() {
        assertThatCode {
            client.takeLoan(ONE)
        }.doesNotThrowAnyException()
    }
}
