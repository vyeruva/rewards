package com.rewards.rewards.service;

import com.rewards.rewards.dto.CustomerRewardsDto;
import com.rewards.rewards.dto.RewardDto;
import com.rewards.rewards.entity.Customer;
import com.rewards.rewards.entity.Transaction;
import com.rewards.rewards.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerRewardsDto getRewardsForCustomer(String customer_uuid) {
        Customer customer = customerRepository.findCustomerWithLastThreeMonthsOfTransactions(customer_uuid);

        return mapToCustomerDto(customer);
    }

    public List<CustomerRewardsDto> getRewardsForAllCustomers() {
        List<Customer> customers = customerRepository.findAllCustomersWithLastThreeMonthsOfTransactions();

        List<CustomerRewardsDto> customerRewardsDtoList = new ArrayList<>();
        customers.stream().map(customer -> mapToCustomerDto(customer)).collect(Collectors.toCollection(() -> customerRewardsDtoList));

        return customerRewardsDtoList;
    }

    private CustomerRewardsDto mapToCustomerDto(Customer customer) {
        CustomerRewardsDto customerRewardsDto = new CustomerRewardsDto();
        customerRewardsDto.setName(customer.getName());
        customerRewardsDto.setId(customer.getUuid());

        Map<Integer, Integer> pointsForMonth = new HashMap<>();
        int totalRewards = 0;

        for(Transaction transaction: customer.getTransactions()) {
            int month = transaction.getDate().get(Calendar.MONTH);
            int year = transaction.getDate().get(Calendar.YEAR);
            int monthAndYear = (year*100) + month + 1; // as the month is zero indexed

            int totalPointsForMonth = pointsForMonth.getOrDefault(monthAndYear, 0);
            int pointsForTransaction = getPointsForTransaction(transaction.getTotal().intValue());
            totalRewards += pointsForTransaction;
            pointsForMonth.put(monthAndYear, totalPointsForMonth+pointsForTransaction);
        }

        customerRewardsDto.setTotal_rewards(totalRewards);
        List<RewardDto> rewards = new ArrayList<>();

        for(int monthAndYear: pointsForMonth.keySet()){
            int month = monthAndYear % 100;
            int year = monthAndYear / 100;
            RewardDto rewardDto = new RewardDto();
            rewardDto.setMonth(month);
            rewardDto.setYear(year);
            rewardDto.setRewards(pointsForMonth.get(monthAndYear));

            rewards.add((rewardDto));
        }
        customerRewardsDto.setRewards(rewards);

        return customerRewardsDto;
    }

    // 101 - 50 + 1*2 = 52
    private int getPointsForTransaction(int total){
        if(total <=50) return 0;
        if(total <=100) return total-50;
        return (total-50) + (total-100);
    }
}

