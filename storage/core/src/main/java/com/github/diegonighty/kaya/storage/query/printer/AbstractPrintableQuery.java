package com.github.diegonighty.kaya.storage.query.printer;

import com.github.diegonighty.kaya.Obj2;
import com.github.diegonighty.kaya.storage.error.NotFoundPrintableError;
import com.github.diegonighty.kaya.storage.error.ReturnTypeError;
import com.github.diegonighty.kaya.storage.processor.element.RepositoryElement;
import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.QueryContext;
import com.github.diegonighty.kaya.storage.query.printer.body.BodyContext;
import com.github.diegonighty.kaya.storage.query.printer.body.BodyPrinter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPrintableQuery implements PrintableQuery {

    private final QueryContext context;
    private final Map<String, BodyPrinter> bodyPrinters = new HashMap<>();

    protected AbstractPrintableQuery(QueryContext context) {
        this.context = context;
        loadPrinters();
    }

    @Override
    public void print(
            MethodPrinter printer,
            RepositoryMethodElement specification,
            RepositoryElement repository,
            String sourceVariable
    ) {
        var returnType = context.returnType();
        var bodyId = context.bodyIdentifier();

        printer.printTab()
                .printMethodSignature(specification)
                .printTab();

        Obj2.of(bodyPrinters.get(bodyId))
                .orThrow(() -> new NotFoundPrintableError(bodyId, getName()))
                .print(new BodyContext(returnType, printer, specification, sourceVariable, repository));

        printer.printEndBracket();
    }

    protected void expectsReturnsEntity(BodyContext ctx) {
        var specification = ctx.specification();
        var entityClazz = ctx.repository().entityClazz();

        specification.returnTypeChecker()
                .is(entityClazz)
                .ifNotThrow((expected) -> new ReturnTypeError(specification, expected));
    }

    protected void expectsReturnsEntityList(BodyContext ctx) {
        var specification = ctx.specification();
        var entityClazz = ctx.repository().entityClazz();

        specification.returnTypeChecker()
                .is(List.class, entityClazz)
                .ifNotThrow((expected) -> new ReturnTypeError(specification, expected));
    }

    protected void expectsReturns(BodyContext ctx, Class<?> expected) {
        var specification = ctx.specification();

        specification.returnTypeChecker()
                .is(expected)
                .ifNotThrow((expectedClazz) -> new ReturnTypeError(specification, expectedClazz));
    }

    protected abstract void loadPrinters();

    protected abstract String getName();

    protected void addPrinter(String method, BodyPrinter printer) {
        bodyPrinters.put(method, printer);
    }

    @Override
    public QueryContext context() {
        return context;
    }
}
