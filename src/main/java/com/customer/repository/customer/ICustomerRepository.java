package com.customer.repository.customer;

import com.customer.model.Customer;
import com.customer.repository.IGeneralRepository;

import java.util.List;

public interface ICustomerRepository extends IGeneralRepository<Customer> {

    List<Customer> getByName(String name);
}
