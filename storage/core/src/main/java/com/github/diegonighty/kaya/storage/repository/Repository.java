package com.github.diegonighty.kaya.storage.repository;

import java.util.List;

public interface Repository<E, ID> {

    long save(E entity);

    void saveAll(List<E> entities);

    E findById(ID id);

    boolean existsById(ID id);

    List<E> findAll();

    long count();

    long deleteById(ID id);

    long delete(E entity);

    long deleteAll(List<? extends E> entities);

    long deleteAllByIds(List<ID> ids);

    void deleteAll();

}
