package io.github.aplotnikov.spock.vs.kotest.entities

import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.TEN

import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Subject
import spock.lang.Title

@Title('Unit test for client entity')
@Stepwise
class ClientSpec extends Specification {

    @Shared
    String clientFirstName = 'Andrii'

    @Shared
    String clientSecondName = 'Plotnikov'

    @Subject
    @Shared
    Client client = new Client(clientFirstName, clientSecondName, ['test@gmail.com', 'test2@gmail.com'])

    void setupSpec() {
        println 'Specification is prepared'
    }

    void cleanupSpec() {
        println 'Specification preparation is destroyed'
    }

    void setup() {
        println 'Unit test is prepared'
    }

    void cleanup() {
        println 'Unit test preparation is destroyed'
    }

    void 'client should have correct first name and second name'() {
        expect:
            with(client) {
                firstName == clientFirstName
                secondName == clientSecondName
            }
    }

    void 'client should have correct first name and second name - verify all'() {
        expect:
            verifyAll {
                with(client) {
                    firstName == clientFirstName
                    secondName == clientSecondName
                }
            }
    }

    @Ignore
    void 'test should be ignored'() {
        expect:
            throw new IllegalStateException('This test should be not launched')
    }

    void 'client should have correct e-mail addresses'() {
        expect:
            client.emails == ['test@gmail.com', 'test2@gmail.com']
    }

    void 'client should have status unknown'() {
        expect:
            client.unknown
    }

    void 'client should be not able to take a loan when he has unknown status'() {
        when:
            client.takeLoan(TEN)
        then:
            IllegalStateException exception = thrown()
            exception.message == 'In order to take a loan client should have status identified. Current status is UNKNOWN'
    }

    void 'client should have status registered when registration is completed'() {
        when:
            client.register()
        then:
            client.registered
    }

    void 'client should be not able to take a loan when he has registered status'() {
        when:
            client.takeLoan(TEN)
        then:
            IllegalStateException exception = thrown()
            exception.message == 'In order to take a loan client should have status identified. Current status is REGISTERED'
    }

    void 'client should have status identified when identification is completed'() {
        when:
            client.identify()
        then:
            client.identified
    }

    void 'client should not have enough money to take a loan'() {
        when:
            client.takeLoan(TEN)
        then:
            IllegalStateException exception = thrown()
            exception.message == 'Client does not have enough money'
    }

    void 'client should have enough money to take a loan'() {
        when:
            client.takeLoan(ONE)
        then:
            noExceptionThrown()
    }

}
