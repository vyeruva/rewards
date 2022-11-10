package com.rewards.rewards.repository;

import com.rewards.rewards.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  // join fetch is used to avoid n+1 queries during fetch
  @Query("SELECT DISTINCT customer FROM Customer customer " +
      "JOIN FETCH customer.transactions transactions " +
      "WHERE transactions.date >= DATEADD(DAY, -90, LOCALTIMESTAMP(3)) "+
      "AND customer.uuid = ?#{#uuid}"
  )
  Customer findCustomerWithLastThreeMonthsOfTransactions(@Param("uuid") String uuid);

  @Query("SELECT DISTINCT customer FROM Customer customer " +
      "JOIN FETCH customer.transactions transactions " +
      "WHERE transactions.date >= DATEADD(DAY, -90, LOCALTIMESTAMP(3)) "
  )
  List<Customer> findAllCustomersWithLastThreeMonthsOfTransactions();


}
