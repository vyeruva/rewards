package com.rewards.rewards.dto;

import java.util.List;

public class CustomerRewardsDto {
    private String name;
    private String id;
    private Integer total_rewards;
    private List<RewardDto> rewards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotal_rewards() {
        return total_rewards;
    }

    // do not like this message naming at all but don't want to expose camel cased vars to the outside world
    public void setTotal_rewards(Integer total_rewards) {
        this.total_rewards = total_rewards;
    }

    public List<RewardDto> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardDto> rewards) {
        this.rewards = rewards;
    }
}
