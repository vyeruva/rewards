package com.rewards.rewards.entity;

import javax.persistence.*;
import java.util.Calendar;
@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String vendor;

  @Column(nullable = false)
  private Calendar date;

  @Column(nullable = false)
  private Double total;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public Calendar getDate() {
    return date;
  }

  public void setDate(Calendar date) {
    this.date = date;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }
}
