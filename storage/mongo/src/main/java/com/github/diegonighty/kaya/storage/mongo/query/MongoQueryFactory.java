package com.github.diegonighty.kaya.storage.mongo.query;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.PrintableQuery;
import com.github.diegonighty.kaya.storage.query.QueryContext;
import com.github.diegonighty.kaya.storage.query.QueryFactory;

public class MongoQueryFactory implements QueryFactory {

    @Override
    public PrintableQuery create(RepositoryMethodElement methodElement) {
        String methodName = methodElement.name().toString();
        String[] terms = methodName.split("_");

        if (terms.length < 2) {
            throw new IllegalArgumentException(
                    "Invalid method name: " + methodName + ". Expected: <type>_<field>"
            );
        }

        QueryContext context = new QueryContext(terms[0], terms[1]);

        return new MongoPrintableQuery(context);
    }

}
