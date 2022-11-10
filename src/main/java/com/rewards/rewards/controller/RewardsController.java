package com.rewards.rewards.controller;

import com.rewards.rewards.dto.CustomerRewardsDto;
import com.rewards.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rewards")
public class RewardsController {

  @Autowired
  private RewardsService rewardsService;

  @GetMapping("/{id}")
  public @ResponseBody CustomerRewardsDto getRewardsForCustomer(@PathVariable String id) {
    return rewardsService.getRewardsForCustomer(id);
  }

  @GetMapping("")
  public @ResponseBody List<CustomerRewardsDto> getRewardsForAllCustomers() {
    return rewardsService.getRewardsForAllCustomers();
  }
}
