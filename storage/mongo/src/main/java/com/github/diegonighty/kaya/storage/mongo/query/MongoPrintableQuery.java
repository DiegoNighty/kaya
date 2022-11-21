package com.github.diegonighty.kaya.storage.mongo.query;

import com.github.diegonighty.kaya.storage.query.QueryContext;
import com.github.diegonighty.kaya.storage.query.printer.AbstractPrintableQuery;
import com.github.diegonighty.kaya.storage.query.printer.MethodPrinter;
import com.github.diegonighty.kaya.storage.query.printer.body.BodyContext;

public class MongoPrintableQuery extends AbstractPrintableQuery {

    public MongoPrintableQuery(QueryContext context) {
        super(context);
    }

    private final static String FIND_ONE = "findOne";
    private final static String FIND_MANY = "findMany";
    private final static String DELETE_MANY = "deleteMany";

    @Override
    protected void loadPrinters() {
        addPrinter(FIND_MANY, (bodyCtx) -> {
            expectsReturnsEntityList(bodyCtx);

            printTemplate(bodyCtx, bodyCtx.printer(), context())
                    .print(".into(new ArrayList<>())")
                    .printEndLineCode();
        });

        addPrinter(FIND_ONE, (bodyCtx) -> {
            expectsReturnsEntity(bodyCtx);

            printTemplate(bodyCtx, bodyCtx.printer(), context())
                    .print(".first()")
                    .printEndLineCode();
        });

        addPrinter(DELETE_MANY, (bodyCtx) -> {
            expectsReturns(bodyCtx, long.class);

            printTemplate(bodyCtx, bodyCtx.printer(), context(), DELETE_MANY)
                    .print(".getDeletedCount()")
                    .printEndLineCode();
        });
    }

    @Override
    protected String getName() {
        return "MongoRepository";
    }

    private MethodPrinter printTemplate(BodyContext bodyCtx, MethodPrinter printer, QueryContext queryCtx) {
        return printTemplate(bodyCtx, printer, queryCtx, queryCtx.method());
    }

    private MethodPrinter printTemplate(BodyContext bodyCtx, MethodPrinter printer, QueryContext queryCtx, String method) {
        return printer.printReturn()
                .print(bodyCtx.sourceVariable()).print(".").print(method)
                .printStartParenthesis()
                .print(queryCtx.filter())
                .printEndParenthesis();
    }
}
