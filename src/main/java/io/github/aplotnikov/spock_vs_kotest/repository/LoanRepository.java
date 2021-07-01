package io.github.aplotnikov.spock_vs_kotest.repository;

import io.github.aplotnikov.spock_vs_kotest.entities.Loan;

public interface LoanRepository {

    Loan save(Loan loan);
}
