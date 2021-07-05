package io.github.aplotnikov.spock_vs_kotest.service

import io.github.aplotnikov.spock_vs_kotest.entities.Application
import io.github.aplotnikov.spock_vs_kotest.entities.Term
import io.github.aplotnikov.spock_vs_kotest.entities.Term.days
import io.github.aplotnikov.spock_vs_kotest.entities.Term.years
import io.github.aplotnikov.spock_vs_kotest.repository.LoanRepository
import io.kotest.assertions.asClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class LoanServiceKoTest : ShouldSpec() {

    override fun isolationMode() = IsolationMode.InstancePerTest

    private val repository = mockk<LoanRepository>()

    private val service = LoanService(repository)

    init {
        should("not pass validation") {
            forAll(
                table(
                    headers("amount", "term", "violation"),
                    row(
                        0.0,
                        days(30),
                        "Application amount is less than zero. Provided amount is 0.0"
                    ),
                    row(
                        10.0,
                        years(1),
                        "Application term is bigger than 3 months. Provided term is 365 days"
                    ),
                )
            ) { amount: Double, term: Term, violation: String ->
                // given
                val application = Application(amount.toBigDecimal(), term)
                // when
                val result = service.create(application)
                // then
                result.asClue {
                    it.isInvalid.shouldBeTrue()
                    it.error shouldBe violation
                }
            }
        }

        should("pass validation and loan is created") {
            // given
            val application = Application(10.0.toBigDecimal(), days(30))
            // and
            every { repository.save(any()) } returnsArgument 0
            // when
            val result = service.create(application)
            // then
            result.isValid.shouldBeTrue()
            // and
            result.get().asClue {
                it.amount shouldBeEqualComparingTo application.amount
                it.term shouldBe application.term
            }
            // and
            verify {
                repository.save(any())
            }
        }
    }
}
