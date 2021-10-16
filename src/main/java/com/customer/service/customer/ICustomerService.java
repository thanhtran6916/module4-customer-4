package com.customer.service.customer;

import com.customer.model.Customer;
import com.customer.service.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer> {

    List<Customer> getByName(String name);
}
