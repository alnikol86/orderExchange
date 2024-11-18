package com.nikolaev.orderexchange.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    Optional<T> findById (Integer id);
    List<T> findAll();
    void save (T entity);
    void update(T entity);
    void delete(T entity);
}
