package com.rewards.rewards.repository;

import com.rewards.rewards.entity.Customer;
import com.rewards.rewards.entity.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  public void filterDataOlderThanThreeMonthsForACustomer() {

    createCustomerArthemis();

    // forces synchronization to DB
    testEntityManager.flush();
    // clears persistence context
    // all entities are now detached and can be fetched again
    testEntityManager.clear();

    int numberOfTransactions = customerRepository.
        findCustomerWithLastThreeMonthsOfTransactions("123e4567-e89b-12d3-a456-426655440000").
        getTransactions().
        size();

    Assert.assertEquals(numberOfTransactions, 1);
  }
  @Test
  public void filtersDataOlderThanThreeMonthsForAllCustomers() {
    Customer customerArthemis = createCustomerArthemis();
    Customer customerAres = createCustomerAres();

    // forces synchronization to DB
    testEntityManager.flush();
    // clears persistence context
    // all entities are now detached and can be fetched again
    testEntityManager.clear();

    List<Customer> allCustomers = customerRepository.
        findAllCustomersWithLastThreeMonthsOfTransactions();

    Assert.assertEquals(allCustomers.size(),2);
    Assert.assertEquals(allCustomers.get(0).getTransactions().size(),1);
    Assert.assertEquals(allCustomers.get(1).getTransactions().size(),1);
  }

  private Customer createCustomerArthemis() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Artemis");
    customer.setUuid("123e4567-e89b-12d3-a456-426655440000");

    Transaction transactionBeforeThreeMonths = new Transaction();
    transactionBeforeThreeMonths.setDate(new GregorianCalendar(2022, Calendar.JUNE,1));
    transactionBeforeThreeMonths.setVendor("walmart");
    transactionBeforeThreeMonths.setTotal(100.00);

    customer.getTransactions().add(transactionBeforeThreeMonths);


    Transaction transactionWithinThreeMonths = new Transaction();
    transactionWithinThreeMonths.setDate(new GregorianCalendar(2022,Calendar.OCTOBER,8));
    transactionWithinThreeMonths.setVendor("walmart");
    transactionWithinThreeMonths.setTotal(102.00);

    customer.getTransactions().add(transactionWithinThreeMonths);

    customerRepository.save(customer);

    // forces synchronization to DB
    testEntityManager.flush();
    // clears persistence context
    // all entities are now detached and can be fetched again
    testEntityManager.clear();

    return customer;
  }

  private Customer createCustomerAres(){
    Customer customerAres= new Customer();
    customerAres.setId(2);
    customerAres.setName("Ares");
    customerAres.setUuid("123e4567-e89b-12d3-a456-426655441111");

    Transaction transactionBeforeThreeMonthsForAres = new Transaction();
    transactionBeforeThreeMonthsForAres.setDate(new GregorianCalendar(2022,Calendar.JUNE,1));
    transactionBeforeThreeMonthsForAres.setVendor("walmart");
    transactionBeforeThreeMonthsForAres.setTotal(100.00);

    customerAres.getTransactions().add(transactionBeforeThreeMonthsForAres);


    Transaction transactionWithinThreeMonthsForAres = new Transaction();
    transactionWithinThreeMonthsForAres.setDate(new GregorianCalendar(2022,Calendar.OCTOBER,8));
    transactionWithinThreeMonthsForAres.setVendor("walmart");
    transactionWithinThreeMonthsForAres.setTotal(102.00);

    customerAres.getTransactions().add(transactionWithinThreeMonthsForAres);

    customerRepository.save(customerAres);

    // forces synchronization to DB
    testEntityManager.flush();
    // clears persistence context
    // all entities are now detached and can be fetched again
    testEntityManager.clear();

    return customerAres;

  }
}
