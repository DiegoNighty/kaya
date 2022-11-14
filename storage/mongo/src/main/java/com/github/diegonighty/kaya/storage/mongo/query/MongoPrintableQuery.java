package com.github.diegonighty.kaya.storage.mongo.query;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.PrintContext;
import com.github.diegonighty.kaya.storage.query.PrintableQuery;
import com.github.diegonighty.kaya.storage.query.QueryContext;

import java.util.List;

public record MongoPrintableQuery(QueryContext context) implements PrintableQuery {

    static final List<String> ACCEPTED_TYPES = List.of("find", "deleteOne", "deleteMany", "countDocuments", "replaceOne");

    public MongoPrintableQuery {
        if (!ACCEPTED_TYPES.contains(context.type())) {
            throw new IllegalArgumentException("Invalid type: " + context.type());
        }
    }

    @Override
    public void print(PrintContext ctx) {
        RepositoryMethodElement method = ctx.method();

        ctx.printOverride()
                .print("public").space()
                .print(method.returnType()).space()
                .print(method.name())
                .printParameters()
                .space().print("{").endLine()
                .printTab()
                .startReturnMono()
                .print(ctx.sourceVariable()).print(".").print(context.type())
                .print("(")
                .print("Filters.eq(").quote()
                .print(context.field()).quote()
                .print(", ")
                .printParameterName(0)
                .print("))")
                .endReturn()
                .print("}")
                .endLine()
                .printTab();

        System.out.println(ctx.builder());
    }
}
