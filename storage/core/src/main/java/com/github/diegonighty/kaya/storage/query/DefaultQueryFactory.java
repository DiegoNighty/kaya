package com.github.diegonighty.kaya.storage.query;

import com.github.diegonighty.kaya.storage.error.InvalidMethodNameError;
import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.filter.FilterSequence;
import com.github.diegonighty.kaya.storage.query.io.PrintableQuery;

import java.util.Arrays;

public class DefaultQueryFactory implements QueryFactory {

    //todo: add support for configuration
    static final String GETTER_PREFIX = "get";

    static final int END_OF_METHOD_DECORATORS = 3;
    static final int REQUIRED_METHOD_TERMS = 4;

    static final int METHOD_NAME_INDEX = 0;
    static final int METHOD_RETURN_TYPE_INDEX = 1;

    private final FilterFactory filterFactory;
    private final DelegatedQueryFactory queryFactory;

    public DefaultQueryFactory(FilterFactory filterFactory, DelegatedQueryFactory delegatedQueryFactory) {
        this.filterFactory = filterFactory;
        this.queryFactory = delegatedQueryFactory;
    }

    @Override
    public PrintableQuery create(RepositoryMethodElement methodElement) {
        var methodName = methodElement.name().toString();

        var terms = methodName.split("(?=\\p{Upper})");
        var termSize = terms.length;

        if (termSize < REQUIRED_METHOD_TERMS) {
            throw new InvalidMethodNameError(methodName, REQUIRED_METHOD_TERMS);
        }

        var startFilter = termSize - END_OF_METHOD_DECORATORS;

        var filter = Arrays.copyOfRange(terms, termSize - startFilter, termSize);
        var filterSequence = filterFactory.create(filter, GETTER_PREFIX);

        return queryFactory.create(
                QueryContext.create(terms[METHOD_NAME_INDEX], terms[METHOD_RETURN_TYPE_INDEX], filterSequence.build())
        );
    }

    public interface FilterFactory {
        FilterSequence create(String[] filter, String getterPrefix);
    }

    public interface DelegatedQueryFactory {
        PrintableQuery create(QueryContext context);
    }
}
