package io.github.aplotnikov.spock_vs_kotest.entities;

public enum DateUnit {
    DAY(1),
    MONTH(30),
    YEAR(365);

    private final int factor;

    DateUnit(int factor) {
        this.factor = factor;
    }

    public int toDays(int number) {
        return number * factor;
    }
}
