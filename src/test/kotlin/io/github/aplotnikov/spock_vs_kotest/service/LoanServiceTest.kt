package io.github.aplotnikov.spock_vs_kotest.service

import io.github.aplotnikov.spock_vs_kotest.entities.Application
import io.github.aplotnikov.spock_vs_kotest.entities.Term
import io.github.aplotnikov.spock_vs_kotest.entities.Term.days
import io.github.aplotnikov.spock_vs_kotest.entities.Term.years
import io.github.aplotnikov.spock_vs_kotest.repository.LoanRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.stream.Stream

class LoanServiceTest {

    private val repository = mock<LoanRepository> {
        on { count() } doReturn 1
    }

    private val service = LoanService(repository)

    companion object {
        @JvmStatic
        fun validationArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(0.0, days(30), "Application amount is less or equal than zero. Provided amount is 0.0"),
            Arguments.of(10.0, years(1), "Application term is bigger than 3 months. Provided term is 365 days"),
        )
    }

    @ParameterizedTest
    @MethodSource("validationArguments")
    fun `should not pass validation`(amount: Double, term: Term, violation: String) {
        // given
        val application = Application(amount.toBigDecimal(), term)
        // when
        val result = service.create(application)
        // then
        result.run {
            assertThat(isInvalid).isTrue
            assertThat(error).isEqualTo(violation)
        }
    }

    @Test
    fun `application should pass validation and loan is created`() {
        // given
        val application = Application(10.0.toBigDecimal(), days(30))
        // and
        whenever(repository.save(any())).then { it.arguments[0] }
        // when
        val result = service.create(application)
        // then
        assertThat(result.isValid).isTrue
        // and
        result.get().run {
            assertThat(amount).isEqualByComparingTo(application.amount)
            assertThat(term).isEqualTo(application.term)
        }
        // and
        verify(repository).save(any())
    }
}
