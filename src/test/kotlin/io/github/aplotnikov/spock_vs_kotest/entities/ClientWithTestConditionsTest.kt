package io.github.aplotnikov.spock_vs_kotest.entities

import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import org.apache.commons.lang3.SystemUtils.IS_OS_LINUX
import org.apache.commons.lang3.SystemUtils.IS_OS_MAC
import org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS
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
        client.asClue {
            it.firstName shouldBe clientFirstName
            it.secondName shouldBe clientSecondName
        }
    }

    @Test
    fun `should be skipped when OS isn't Mac OS - assumeTrue method`() {
        assumeTrue(IS_OS_MAC) {
            "Aborting test: test wasn't launched on Mac OS"
        }
        client.asClue {
            it.firstName shouldBe clientFirstName
            it.secondName shouldBe clientSecondName
        }
    }

    @Test
    fun `should be skipped when OS isn't Linux - assumeTrue method`() {
        assumeTrue(IS_OS_LINUX) {
            "Aborting test: test wasn't launched on Linux"
        }
        client.asClue {
            it.firstName shouldBe clientFirstName
            it.secondName shouldBe clientSecondName
        }
    }

    @Test
    fun `should be skipped when OS isn't Windows - assumingThat method`() {
        assumingThat(IS_OS_WINDOWS) {
            println("Inner assertion is called")
            client.firstName shouldBe clientFirstName
        }
        println("Main assertion is called")
        client.secondName shouldBe clientSecondName
    }

    @Test
    fun `should be skipped when OS isn't Mac OS - assumingThat method`() {
        assumingThat(IS_OS_MAC) {
            println("Inner assertion is called")
            client.firstName shouldBe clientFirstName
        }
        println("Main assertion is called")
        client.secondName shouldBe clientSecondName
    }

    @Test
    fun `should be skipped when OS isn't Linux - assumingThat method`() {
        assumingThat(IS_OS_LINUX) {
            println("Inner assertion is called")
            client.firstName shouldBe clientFirstName
        }
        println("Main assertion is called")
        client.secondName shouldBe clientSecondName
    }
}
