package com.github.diegonighty.kaya.storage.query.printer.body;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryElement;
import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.QueryContext;
import com.github.diegonighty.kaya.storage.query.printer.MethodPrinter;

public record BodyContext(
        QueryContext.ReturnQuantity quantity,
        MethodPrinter printer,
        RepositoryMethodElement specification,
        String sourceVariable,
        RepositoryElement repository
) {
}
