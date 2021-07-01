package io.github.aplotnikov.spock_vs_kotest.entities;

import java.math.BigDecimal;

public class Loan {

    private final BigDecimal amount;

    private final Term term;

    public Loan(BigDecimal amount, Term term) {
        this.amount = amount;
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Term getTerm() {
        return term;
    }
}
