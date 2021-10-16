package com.customer.repository.customer;

import com.customer.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class CustomerRepository implements ICustomerRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> getAll() {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer as c", Customer.class);
        return query.getResultList();
    }

    @Override
    public void save(Customer customer) {
        if (customer.getId() == null) {
            entityManager.persist(customer);
        } else {
            entityManager.merge(customer);
        }
    }

    @Override
    public void delete(Long id) {
        Customer customer = this.getById(id);
        entityManager.remove(customer);
    }

    @Override
    public Customer getById(Long id) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer as c where c.id = :id", Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Customer> getByName(String name) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer as c where c.name like :name", Customer.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
