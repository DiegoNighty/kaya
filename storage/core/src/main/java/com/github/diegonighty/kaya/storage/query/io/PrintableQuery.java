package com.github.diegonighty.kaya.storage.query.io;

import com.github.diegonighty.kaya.storage.query.QueryContext;

public interface PrintableQuery {

    QueryContext context();

    void print(PrintContext context);

}
