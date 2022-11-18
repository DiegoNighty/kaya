package com.github.diegonighty.kaya.storage.query;

public record QueryContext(
        String method,
        String returnType,
        String filter
) {}
