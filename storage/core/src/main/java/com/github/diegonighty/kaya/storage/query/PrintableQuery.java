package com.github.diegonighty.kaya.storage.query;

public interface PrintableQuery {

    // <type> By <optional decorator> <field>
    // find By Distinct Id
    // find By Id
    // find By Id

    QueryContext context();

    void print(PrintContext context);

}
