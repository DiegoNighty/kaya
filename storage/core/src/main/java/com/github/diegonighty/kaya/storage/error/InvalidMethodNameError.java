package com.github.diegonighty.kaya.storage.error;

public class InvalidMethodNameError extends RuntimeException {

    public InvalidMethodNameError(String methodName, int expectedMethodTerms) {
        super(String.format("Invalid method name: %s, Method name must have at least %s terms", methodName, expectedMethodTerms));
    }

}
