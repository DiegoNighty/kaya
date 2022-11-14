package com.github.diegonighty.kaya.storage.repository;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReactiveRepository<E, ID> {

    Mono<Long> save(E entity);

    Mono<Void> saveAll(List<E> entities);

    Mono<Void> saveAll(Publisher<List<E>> publisher);

    Mono<E> findById(ID id);

    Mono<E> findById(Publisher<ID> id);

    Mono<Boolean> existsById(ID id);

    Mono<Boolean> existsById(Publisher<ID> id);

    Mono<List<E>> findAll();

    Mono<Long> count();

    Mono<Void> deleteById(ID id);

    Mono<Void> deleteById(Publisher<ID> id);

    Mono<Void> delete(E entity);

    Mono<Void> deleteAll(List<? extends E> entities);

    Mono<Void> deleteAll(Publisher<List<E>> entityStream);

    Mono<Void> deleteAllByIds(List<ID> ids);

    Mono<Void> deleteAll();

}
