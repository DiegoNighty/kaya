package com.github.diegonighty.kaya.storage.mongo.query;

import com.github.diegonighty.kaya.storage.query.QueryContext;
import com.github.diegonighty.kaya.storage.query.io.AbstractPrintableQuery;
import com.github.diegonighty.kaya.storage.query.io.MethodPrinter;

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
            checkIfReturnsList(bodyCtx);

            MethodPrinter printer = bodyCtx.printer();
            QueryContext queryCtx = context();

            printer.printReturn()
                    .print(bodyCtx.sourceVariable()).print(".").print(queryCtx.method())
                    .printStartParenthesis()
                    .print(queryCtx.filter())
                    .printEndParenthesis()
                    .print(".into(new ArrayList<>())")
                    .printEndLineCode();
        });

        addPrinter(FIND_ONE, (bodyCtx) -> {
            checkIfReturnsEntityList(bodyCtx);

            MethodPrinter printer = bodyCtx.printer();
            QueryContext queryCtx = context();

            printer.printReturn()
                    .print(bodyCtx.sourceVariable()).print(".").print(queryCtx.method())
                    .printStartParenthesis()
                    .print(queryCtx.filter())
                    .printEndParenthesis()
                    .print(".first()")
                    .printEndLineCode();
        });
    }

    @Override
    protected String getName() {
        return "Mongo";
    }
}
