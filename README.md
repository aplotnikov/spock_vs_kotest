[![CircleCI](https://dl.circleci.com/status-badge/img/gh/aplotnikov/spock_vs_kotest/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/aplotnikov/spock_vs_kotest/tree/main)

#### Spock vs Kotest

This repository is created for education reason.

The technology stack: Java 17, Groovy, Gradle, JUnit 5.9.3, Spock 2.3, Kotlin 1.8.21, 
kotest 5.6.1, mockito-kotlin, mockk
Static code analyzers (codenarc, detekt, ktlint)

It contains examples of using new features from JUnit 5 vs Spock features.
It is possible to find the following examples of comparison:
1. Assertions: Spock power assertions vs kotest assertions
2. Exception verifications
3. Test life cycles: instance per method, instance per class, order of calling methods
4. Parametrized tests
5. Asynchronous verifications: timeout assertions, awaitility framework, async conditions in Spock and kotest
6. Repeated tests
7. Condition executions: IgnoreIf, Requires, assumptions
8. Using of mocks: Spock mocks vs mockk mocks vs mockito mocks
