package com.customer.service;

import java.util.List;

public interface IGeneralService<T> {
    List<T> getAll();

    void save(T t);

    void delete(Long id);

    T getById(Long id);
}
