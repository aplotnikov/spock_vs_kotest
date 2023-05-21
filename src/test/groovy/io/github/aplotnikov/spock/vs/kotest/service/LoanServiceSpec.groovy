package io.github.aplotnikov.spock.vs.kotest.service

import static io.github.aplotnikov.spock.vs.kotest.entities.Term.days
import static io.github.aplotnikov.spock.vs.kotest.entities.Term.years

import io.github.aplotnikov.spock.vs.kotest.repository.LoanRepository
import io.vavr.control.Validation
import spock.lang.Specification
import spock.lang.Subject

class LoanServiceSpec extends Specification {

    LoanRepository repository = Mock {
        count() >> 1
    }

    @Subject
    LoanService service = new LoanService(repository)

    void 'application should not pass validation when amount is #amount and term is #term'() {
        given:
            io.github.aplotnikov.spock.vs.kotest.entities.Application application = new io.github.aplotnikov.spock.vs.kotest.entities.Application(amount, term)
        when:
            Validation<String, io.github.aplotnikov.spock.vs.kotest.entities.Loan> result = service.create(application)
        then:
            with(result) {
                invalid
                error == violation
            }
        and:
            0 * _
        where:
            amount | term     || violation
            0.0    | days(30) || 'Application amount is less or equal than zero. Provided amount is 0.0'
            10.0   | years(1) || 'Application term is bigger than 3 months. Provided term is 365 days'
    }

    void 'application should pass validation and loan is created'() {
        given:
            io.github.aplotnikov.spock.vs.kotest.entities.Application application = new io.github.aplotnikov.spock.vs.kotest.entities.Application(10.0, days(30))
        when:
            Validation<String, io.github.aplotnikov.spock.vs.kotest.entities.Loan> result = service.create(application)
        then:
            result.valid
        and:
            with(result.get()) {
                amount == application.amount
                term == application.term
            }
        and:
            with(repository) {
                1 * save(_ as io.github.aplotnikov.spock.vs.kotest.entities.Loan) >> { io.github.aplotnikov.spock.vs.kotest.entities.Loan loan -> loan }
            }
        and:
            0 * _
    }

}