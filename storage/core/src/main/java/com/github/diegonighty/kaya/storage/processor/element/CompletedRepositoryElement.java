package com.github.diegonighty.kaya.storage.processor.element;

import java.util.List;

public record CompletedRepositoryElement(
        RepositoryElement repository,
        List<RepositoryMethodElement> methods
) { }
