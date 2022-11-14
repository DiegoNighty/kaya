package com.github.diegonighty.kaya.storage.query;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;

public interface QueryFactory {

    PrintableQuery create(RepositoryMethodElement methodElement);

}
