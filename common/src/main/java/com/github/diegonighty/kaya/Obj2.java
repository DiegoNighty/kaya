package com.github.diegonighty.kaya;

import java.util.function.Supplier;

public record Obj2<T>(T value) {

    public static <T> Obj2<T> of(T value) {
        return new Obj2<>(value);
    }

    public T orThrow(RuntimeException exception) {
        if (value == null) {
            throw exception;
        }

        return value;
    }

    public T orThrow(Supplier<RuntimeException> exceptionSupplier) {
        if (value == null) {
            throw exceptionSupplier.get();
        }

        return value;
    }

    public T orElse(T other) {
        if (value == null) {
            return other;
        }

        return value;
    }

}
