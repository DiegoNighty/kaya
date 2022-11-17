package com.github.diegonighty.kaya.storage.query;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.io.PrintableQuery;

public interface QueryFactory {

    PrintableQuery create(RepositoryMethodElement methodElement);

}
