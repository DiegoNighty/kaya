package com.github.diegonighty.kaya.storage.query;

public interface PrintableQuery {

    QueryContext context();

    void print(PrintContext context);

}
