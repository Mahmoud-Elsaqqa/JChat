package com.java.iti.client.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDOperation <T>{
    List<T> findAll();

    Optional<T> findById(int id);

    Optional<T> update(T entity, int id);

    T save(T entity);

    void delete(int id);
}
