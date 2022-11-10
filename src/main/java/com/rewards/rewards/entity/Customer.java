package com.rewards.rewards.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = @Index(columnList = "uuid")) // as uuid is used for lookups
public class Customer {
    // care is taken to never expose this to the customer.
    // Rather the UUID is the key used for customer lookups in the API
    // This is for internal use only
    // An argument can be made to remove this  id and use uuid as PK, but I am not very comfortable exposing DB
    // PKs to the customer
    @Id
    private Integer id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String name;

    // using eager load to avoid n+1 queries
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
