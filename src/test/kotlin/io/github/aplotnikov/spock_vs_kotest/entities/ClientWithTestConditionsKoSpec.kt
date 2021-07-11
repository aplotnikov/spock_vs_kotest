package io.github.aplotnikov.spock_vs_kotest.entities

import io.kotest.assertions.asClue
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.apache.commons.lang3.SystemUtils.IS_OS_LINUX
import org.apache.commons.lang3.SystemUtils.IS_OS_MAC
import org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS

class ClientWithTestConditionsKoSpec : ShouldSpec() {

    private val clientFirstName = "Andrii"

    private val clientSecondName = "Plotnikov"

    private val client = Client(clientFirstName, clientSecondName, listOf("test@gmail.com", "test2@gmail.com"))

    init {
        should("be skipped when OS isn't Windows").config(enabled = IS_OS_WINDOWS) {
            client.asClue {
                it.firstName shouldBe clientFirstName
                it.secondName shouldBe clientSecondName
            }
        }

        should("be skipped when OS isn't Linux").config(enabled = IS_OS_LINUX) {
            client.asClue {
                it.firstName shouldBe clientFirstName
                it.secondName shouldBe clientSecondName
            }
        }

        should("be skipped when OS isn't Mac OS").config(enabled = IS_OS_MAC) {
            client.asClue {
                it.firstName shouldBe clientFirstName
                it.secondName shouldBe clientSecondName
            }
        }
    }
}
