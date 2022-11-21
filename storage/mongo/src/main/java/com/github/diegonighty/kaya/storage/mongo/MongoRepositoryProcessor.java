package com.github.diegonighty.kaya.storage.mongo;

import com.github.diegonighty.kaya.storage.mongo.query.MongoFilterSequence;
import com.github.diegonighty.kaya.storage.mongo.query.MongoPrintableQuery;
import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepositoryAnnotation;
import com.github.diegonighty.kaya.storage.processor.RepositoryProcessor;
import com.github.diegonighty.kaya.storage.query.DefaultQueryFactory;
import com.github.diegonighty.kaya.storage.query.QueryFactory;

import java.util.List;

public class MongoRepositoryProcessor extends RepositoryProcessor<MongoRepositoryAnnotation> {

    @Override
    protected List<Class<? extends MongoRepositoryAnnotation>> annotations() {
        return List.of(MongoRepositoryAnnotation.class);
    }

    @Override
    protected QueryFactory getFactory() {
        return new DefaultQueryFactory(MongoFilterSequence::create, MongoPrintableQuery::new);
    }

    @Override
    protected String getTemplatePath() {
        return "MongoRepositoryTemplate.java";
    }
}
