package io.github.aplotnikov.spock_vs_kotest.entities

import org.apache.commons.lang3.SystemUtils.IS_OS_LINUX
import org.apache.commons.lang3.SystemUtils.IS_OS_MAC
import org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Assumptions.assumingThat
import org.junit.jupiter.api.Test

class ClientWithTestConditionsTest {

    private val clientFirstName = "Andrii"

    private val clientSecondName = "Plotnikov"

    private val client = Client(clientFirstName, clientSecondName, listOf("test@gmail.com", "test2@gmail.com"))

    @Test
    fun `should be skipped when OS isn't Windows - assumeTrue method`() {
        assumeTrue(IS_OS_WINDOWS) {
            "Aborting test: test wasn't launched on Windows"
        }
        client.run {
            assertThat(firstName).isEqualTo(clientFirstName)
            assertThat(secondName).isEqualTo(clientSecondName)
        }
    }

    @Test
    fun `should be skipped when OS isn't Mac OS - assumeTrue method`() {
        assumeTrue(IS_OS_MAC) {
            "Aborting test: test wasn't launched on Mac OS"
        }
        client.run {
            assertThat(firstName).isEqualTo(clientFirstName)
            assertThat(secondName).isEqualTo(clientSecondName)
        }
    }

    @Test
    fun `should be skipped when OS isn't Linux - assumeTrue method`() {
        assumeTrue(IS_OS_LINUX) {
            "Aborting test: test wasn't launched on Linux"
        }
        client.run {
            assertThat(firstName).isEqualTo(clientFirstName)
            assertThat(secondName).isEqualTo(clientSecondName)
        }
    }

    @Test
    fun `should be skipped when OS isn't Windows - assumingThat method`() {
        assumingThat(IS_OS_WINDOWS) {
            println("Inner assertion is called")
            assertThat(client.firstName).isEqualTo(clientFirstName)
        }
        println("Main assertion is called")
        assertThat(client.secondName).isEqualTo(clientSecondName)
    }

    @Test
    fun `should be skipped when OS isn't Mac OS - assumingThat method`() {
        assumingThat(IS_OS_MAC) {
            println("Inner assertion is called")
            assertThat(client.firstName).isEqualTo(clientFirstName)
        }
        println("Main assertion is called")
        assertThat(client.secondName).isEqualTo(clientSecondName)
    }

    @Test
    fun `should be skipped when OS isn't Linux - assumingThat method`() {
        assumingThat(IS_OS_LINUX) {
            println("Inner assertion is called")
            assertThat(client.firstName).isEqualTo(clientFirstName)
        }
        println("Main assertion is called")
        assertThat(client.secondName).isEqualTo(clientSecondName)
    }
}
