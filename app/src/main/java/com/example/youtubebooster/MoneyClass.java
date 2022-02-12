package com.example.youtubebooster;

public class MoneyClass {
    private int m;

    public MoneyClass(int m) {
        this.m = m;
    }

    public int getDollars() {
        return m / 100;
    }

    public int getCents() {
        return m % 100;
    }

    public int get() {
        return m;
    }

    public MoneyClass add(MoneyClass other) {
        return new MoneyClass(m + other.get());
    }

    public MoneyClass subtract(MoneyClass other) {
        return new MoneyClass(m - other.get());
    }
}
