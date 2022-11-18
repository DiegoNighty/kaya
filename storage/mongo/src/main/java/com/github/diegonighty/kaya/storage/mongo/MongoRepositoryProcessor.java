package com.github.diegonighty.kaya.storage.mongo;

import com.github.diegonighty.kaya.storage.mongo.query.MongoFilterSequence;
import com.github.diegonighty.kaya.storage.mongo.query.MongoPrintableQuery;
import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepository;
import com.github.diegonighty.kaya.storage.processor.RepositoryProcessor;
import com.github.diegonighty.kaya.storage.query.DefaultQueryFactory;
import com.github.diegonighty.kaya.storage.query.QueryFactory;

import java.util.List;

public class MongoRepositoryProcessor extends RepositoryProcessor<MongoRepository> {

    @Override
    protected List<Class<? extends MongoRepository>> annotations() {
        return List.of(MongoRepository.class);
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
