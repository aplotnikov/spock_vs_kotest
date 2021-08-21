package io.github.aplotnikov.spock_vs_kotest.entities

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal.TEN

class ClientNestedTest {

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

    @Test
    fun `client should have correct first name and second name`() {
        client.run {
            assertThat(firstName).isEqualTo(clientFirstName)
            assertThat(secondName).isEqualTo(clientSecondName)
        }
    }

    @Test
    fun `client should have status unknown`() {
        assertThat(client.isUnknown).isTrue
    }

    @Nested
    @DisplayName("When client is created")
    inner class DefaultStateTest {
        @BeforeEach
        fun setUp() {
            println("Test into ${javaClass.simpleName} class is prepared")
        }

        @AfterEach
        fun tearDown() {
            println("Test into ${javaClass.simpleName} class is cleaned")
        }

        @Test
        fun `client should have correct first name and second name`() {
            client.run {
                assertThat(firstName).isEqualTo(clientFirstName)
                assertThat(secondName).isEqualTo(clientSecondName)
            }
        }

        @Test
        fun `client should have status unknown`() {
            assertThat(client.isUnknown).isTrue
        }

        @Nested
        @DisplayName("When client is created - nested tests")
        inner class DefaultStateTest {
            @BeforeEach
            fun setUp() {
                println("Test into ${javaClass.simpleName} class is prepared")
            }

            @AfterEach
            fun tearDown() {
                println("Test into ${javaClass.simpleName} class is cleaned")
            }

            @Test
            fun `client should have correct first name and second name`() {
                client.run {
                    assertThat(firstName).isEqualTo(clientFirstName)
                    assertThat(secondName).isEqualTo(clientSecondName)
                }
            }

            @Test
            fun `client should have status unknown`() {
                assertThat(client.isUnknown).isTrue
            }
        }
    }

    @Nested
    @DisplayName("When registration is completed")
    inner class RegistrationTest {
        @BeforeEach
        fun setUp() {
            println("Test into ${javaClass.simpleName} class is prepared")
            client.register()
        }

        @AfterEach
        fun tearDown() {
            println("Test into ${javaClass.simpleName} class is cleaned")
        }

        @Test
        fun `client should have status registered when registration is completed`() {
            assertThat(client.isRegistered).isTrue
        }

        @Test
        fun `client should be not able to take a loan when he has registered status`() {
            assertThatThrownBy { client.takeLoan(TEN) }
                .isInstanceOf(IllegalStateException::class.java)
                .hasMessage(
                    "In order to take a loan client should have status identified. Current status is REGISTERED"
                )
        }

        @Nested
        @DisplayName("When identification is completed")
        inner class IdentificationTest {
            @BeforeEach
            fun setUp() {
                println("Test into ${javaClass.simpleName} class is prepared")
                client.identify()
            }

            @AfterEach
            fun tearDown() {
                println("Test into ${javaClass.simpleName} class is cleaned")
            }

            @Test
            fun `client should have status identified when identification is completed`() {
                assertThat(client.isIdentified).isTrue
            }

            @Test
            fun `client should not have enough money to take a loan`() {
                assertThatThrownBy { client.takeLoan(TEN) }
                    .isInstanceOf(IllegalStateException::class.java)
                    .hasMessage(
                        "Client does not have enough money"
                    )
            }
        }
    }
}
