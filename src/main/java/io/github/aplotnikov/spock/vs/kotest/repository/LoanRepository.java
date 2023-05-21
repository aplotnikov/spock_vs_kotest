package io.github.aplotnikov.spock.vs.kotest.repository;

import io.github.aplotnikov.spock.vs.kotest.entities.Loan;

public interface LoanRepository {

    Loan save(Loan loan);

    int count();
}
