package com.github.diegonighty.kaya.storage.query;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.printer.PrintableQuery;

public interface QueryFactory {

    PrintableQuery create(RepositoryMethodElement methodElement);

}
