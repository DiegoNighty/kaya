package com.github.diegonighty.kaya.storage.processor;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryElement;

public record RepositoryContext(String dataSourceVariableName, RepositoryElement element) {}
