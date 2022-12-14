package com.rewards.rewards.service;

import com.rewards.rewards.dto.CustomerRewardsDto;
import com.rewards.rewards.entity.Customer;
import com.rewards.rewards.entity.Transaction;
import com.rewards.rewards.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {
  @InjectMocks
  private RewardsService rewardsService;

  @Mock
  private CustomerRepository customerRepository;

  @Autowired
  private ApplicationContext context;

  @Test
  public void calculatesTheCorrectPoints() {
    Customer customer = createCustomer();

    Mockito.when(customerRepository.
        findCustomerWithLastThreeMonthsOfTransactions("123e4567-e89b-12d3-a456-426655440000")
    ).thenReturn(customer);

    CustomerRewardsDto customerRewardsDto = rewardsService.
        getRewardsForCustomer("123e4567-e89b-12d3-a456-426655440000");

    // TODO: fix workaround as int comparison is something that I was unable to find for some reason
    Assert.assertEquals(customerRewardsDto.getTotal_rewards().toString(), "140");
  }

  private Customer createCustomer() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Artemis");
    customer.setUuid("123e4567-e89b-12d3-a456-426655440000");
    customer.setTransactions(new ArrayList<>());

    Transaction transactionWithTotalFifty = new Transaction();
    transactionWithTotalFifty.setDate(new GregorianCalendar(2022, Calendar.OCTOBER,1));
    transactionWithTotalFifty.setVendor("walmart");
    transactionWithTotalFifty.setTotal(50.00);

    Transaction transactionWithTotalHundred = new Transaction();
    transactionWithTotalHundred.setDate(new GregorianCalendar(2022,Calendar.OCTOBER,1));
    transactionWithTotalHundred.setVendor("walmart");
    transactionWithTotalHundred.setTotal(100.00);

    Transaction transactionWithTotalOneTwenty = new Transaction();
    transactionWithTotalOneTwenty.setDate(new GregorianCalendar(2022,Calendar.OCTOBER,1));
    transactionWithTotalOneTwenty.setVendor("walmart");
    transactionWithTotalOneTwenty.setTotal(120.00);

    Transaction transactionWithTotalFortyNine = new Transaction();
    transactionWithTotalFortyNine.setDate(new GregorianCalendar(2022,Calendar.OCTOBER,1));
    transactionWithTotalFortyNine.setVendor("walmart");
    transactionWithTotalFortyNine.setTotal(49.00);

    customer.getTransactions().add(transactionWithTotalFifty);
    customer.getTransactions().add(transactionWithTotalHundred);
    customer.getTransactions().add(transactionWithTotalOneTwenty);
    customer.getTransactions().add(transactionWithTotalFortyNine);
    return customer;
  }
}
