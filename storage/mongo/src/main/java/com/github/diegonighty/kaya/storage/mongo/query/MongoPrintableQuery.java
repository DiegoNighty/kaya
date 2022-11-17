package com.github.diegonighty.kaya.storage.mongo.query;

import com.github.diegonighty.kaya.storage.query.io.PrintContext;
import com.github.diegonighty.kaya.storage.query.io.PrintableQuery;
import com.github.diegonighty.kaya.storage.query.QueryContext;

import java.util.List;

public record MongoPrintableQuery(QueryContext context) implements PrintableQuery {

    static final List<String> ACCEPTED_TYPES = List.of("find", "deleteOne", "deleteMany", "countDocuments", "replaceOne");

    public MongoPrintableQuery {
        if (!ACCEPTED_TYPES.contains(context.method().value())) {
            throw new IllegalArgumentException("Invalid type: " + context.method().value());
        }
    }

    @Override
    public void print(PrintContext ctx) {
        ctx.printOverride()
                .printMethodDeclaration()
                .startReturnMono()
                .print(ctx.sourceVariable()).print(".").print(context.method().value())
                .print("(")
                .print(context.filter().value())
                .print(")")
                .endReturn()
                .print("}")
                .printTab();
    }
}
