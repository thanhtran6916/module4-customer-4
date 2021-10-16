package com.customer.repository;

import java.util.List;

public interface IGeneralRepository<T> {
    List<T> getAll();

    void save(T t);

    void delete(Long id);

    T getById(Long id);

}
