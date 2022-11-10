package com.rewards.rewards.integrationtests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardsControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void givenCustomerUuidReturn200()
      throws Exception {

    ArrayList<Integer> rewardsPerMonth = new ArrayList<>();
    rewardsPerMonth.add(1234);
    rewardsPerMonth.add(360);
    rewardsPerMonth.add(30);

    this.mockMvc.
        perform(get("/rewards/4a15ea99-40ba-47da-8714-a76dba7e85d3")).
        andDo(print()).
        andExpect(status().isOk()).
        andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Athena")).
        andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4a15ea99-40ba-47da-8714-a76dba7e85d3")).
        andExpect(MockMvcResultMatchers.jsonPath("$.totalRewards").value(1624)).
        andExpect(MockMvcResultMatchers.jsonPath("$.rewards[*].rewards").value(rewardsPerMonth));
  }

  @Test
  public void givenNoCustomerUuidReturn200()
      throws Exception {

    ArrayList<Integer> totalRewardsForCustomer = new ArrayList<>();
    totalRewardsForCustomer.add(1624);
    totalRewardsForCustomer.add(516);

    this.mockMvc.
        perform(get("/rewards")).
        andDo(print()).
        andExpect(status().isOk()).
        andExpect(MockMvcResultMatchers.jsonPath("$.[*].totalRewards").value(totalRewardsForCustomer));
  }

  // TO-DO: Add tests for month and years which are not brittle.
  // Business logic involved in calculating expectations is necessary.
}
