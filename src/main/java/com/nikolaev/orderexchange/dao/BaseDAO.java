package com.nikolaev.orderexchange.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<K, E> {
    Optional<E> findById (K id);
    List<E> findAll();
    E save (E entity);
    void update(E entity);
    boolean delete(E entity);
}
