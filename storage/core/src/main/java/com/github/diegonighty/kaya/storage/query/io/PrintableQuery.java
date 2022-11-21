package com.github.diegonighty.kaya.storage.query.io;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryElement;
import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.QueryContext;

public interface PrintableQuery {

    QueryContext context();

    void print(
            MethodPrinter printer,
            RepositoryMethodElement specification,
            RepositoryElement repository,
            String sourceVariable
    );

}
