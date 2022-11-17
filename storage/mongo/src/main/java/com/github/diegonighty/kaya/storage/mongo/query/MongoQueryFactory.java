package com.github.diegonighty.kaya.storage.mongo.query;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.QueryContext;
import com.github.diegonighty.kaya.storage.query.QueryFactory;
import com.github.diegonighty.kaya.storage.query.io.PrintableQuery;
import com.github.diegonighty.kaya.storage.query.token.QueryToken;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MongoQueryFactory implements QueryFactory {

    static final String QUANTITY_DEFAULT_VALUE = "one";
    static final List<String> LOGICAL_OPERATORS = List.of("and", "or");

    @Override
    public PrintableQuery create(RepositoryMethodElement methodElement) {
        var methodName = methodElement.name().toString();
        List<String> terms = Arrays.asList(methodName.split("(?=\\p{Upper})"));
        int termSize = terms.size();

        if (termSize < 3) {
            throw new IllegalArgumentException(
                    "Invalid method name: " + methodName + ". Expected: <method>By<filter> : findByName"
            );
        }

        QueryToken method = new QueryToken(QueryToken.Type.METHOD, terms.get(0));
        QueryToken quantity = new QueryToken(QueryToken.Type.QUANTITY, QUANTITY_DEFAULT_VALUE);
        int startFilter = 2;

        if (termSize > 4) {
            startFilter = termSize - 3;
            quantity = new QueryToken(QueryToken.Type.QUANTITY, terms.get(1));
        }

        List<String> termList = terms.subList(termSize - startFilter, termSize);

        return new MongoPrintableQuery(
                new QueryContext(method, quantity, new QueryToken(QueryToken.Type.FIELD, buildFilter(termList.iterator(), termList)))
        );
    }

    private String buildFilter(Iterator<String> filter, List<String> terms) {
        StringBuilder builder = new StringBuilder();
        String previousOperator = "";

        int operatorChanges = 0;
        int index = 0;

        while (filter.hasNext()) {
            String term = filter.next().toLowerCase();

            if (!LOGICAL_OPERATORS.contains(term)) {
                int nextOperatorIndex = index + 1;

                if (filter.hasNext() && terms.size() > nextOperatorIndex) {
                    String nextOperator = terms.get(nextOperatorIndex).toLowerCase();

                    if (LOGICAL_OPERATORS.contains(nextOperator) && !previousOperator.equals(nextOperator)) {
                        builder.append("Filters.")
                                .append(nextOperator)
                                .append("(");

                        previousOperator = nextOperator;
                        operatorChanges++;
                    }
                }

                builder.append("Filters.eq(\"").append(term).append("\", ").append(term).append(")");

                if (filter.hasNext()) {
                    builder.append(", ");
                }
            }

            index++;
        }

        return builder.append(")".repeat(operatorChanges))
                .toString();
    }

}
