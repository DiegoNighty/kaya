package com.github.diegonighty.kaya.storage.mongo;

import com.github.diegonighty.kaya.storage.mongo.repository.MongoReactiveRepository;
import com.mongodb.client.model.Filters;
import org.mongojack.JacksonMongoCollection;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Test implements MongoReactiveRepository<TestEntity, String> {

    private final JacksonMongoCollection<TestEntity> collection;

    public Test(JacksonMongoCollection<TestEntity> collection) {
        this.collection = collection;
    }

    @Override
    public Mono<Long> save(TestEntity entity) {
        return Mono.fromSupplier(() -> collection.save(entity).getModifiedCount());
    }

    @Override
    public Mono<Void> saveAll(List<TestEntity> entities) {
        return Mono.fromRunnable(() -> collection.insert(entities));
    }

    @Override
    public Mono<Void> saveAll(Publisher<List<TestEntity>> publisher) {
        return Mono.from(publisher)
                .flatMap(this::saveAll);
    }

    @Override
    public Mono<TestEntity> findById(String s) {
        return Mono.fromSupplier(() -> collection.findOneById(s));
    }

    @Override
    public Mono<TestEntity> findById(Publisher<String> id) {
        return Mono.from(id)
                .flatMap(this::findById);
    }

    @Override
    public Mono<Boolean> existsById(String s) {
        return Mono.fromSupplier(() -> collection.findOneById(s) != null);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<String> id) {
        return Mono.from(id)
                .flatMap(this::existsById);
    }

    @Override
    public Mono<List<TestEntity>> findAll() {
        return Mono.fromSupplier(() -> collection.find().into(new ArrayList<>()));
    }

    @Override
    public Mono<Long> count() {
        return Mono.fromSupplier(collection::countDocuments);
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return Mono.fromRunnable(() -> collection.removeById(s));
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> id) {
        return Mono.from(id)
                .flatMap(this::deleteById);
    }

    @Override
    public Mono<Void> delete(TestEntity entity) {
        return Mono.fromRunnable(() -> collection.removeById(entity.getId()));
    }

    @Override
    public Mono<Void> deleteAll(List<? extends TestEntity> entities) {
        return Mono.fromRunnable(() -> collection.deleteMany(Filters.all("id", entities.stream().map(TestEntity::getId).toArray())));
    }

    @Override
    public Mono<Void> deleteAllByIds(List<String> ids) {
        return Mono.fromRunnable(() -> collection.deleteMany(Filters.all("id", ids.toArray())));
    }

    @Override
    public Mono<Void> deleteAll(Publisher<List<TestEntity>> entityStream) {
        return Mono.from(entityStream)
                .flatMap(this::deleteAll);
    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.fromRunnable(collection::drop);
    }
}
