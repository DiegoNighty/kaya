package com.github.diegonighty.kaya.storage.mongo.query;

import com.github.diegonighty.kaya.storage.query.QueryContext;
import com.github.diegonighty.kaya.storage.query.io.PrintContext;
import com.github.diegonighty.kaya.storage.query.io.PrintableQuery;

public record MongoPrintableQuery(QueryContext context) implements PrintableQuery {

    @Override
    public void print(PrintContext ctx) {
        ctx.printOverride()
                .printMethodDeclaration()
                .startReturnMono()
                .print(ctx.sourceVariable()).print(".").print(context.method())
                .print("(")
                .print(context.filter())
                .print(")")
                .endReturn()
                .print("}")
                .printTab();
    }
}
