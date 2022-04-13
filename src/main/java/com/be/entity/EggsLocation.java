package com.be.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
/*
 * @Entity
 * 
 * @Table(name = "eggs_location") public class EggsLocation {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 * 
 * private String area; private String city;
 * 
 * public Long getId() { return id; } public void setId(Long id) { this.id = id;
 * } public String getArea() { return area; } public void setArea(String area) {
 * this.area = area; } public String getCity() { return city; } public void
 * setCity(String city) { this.city = city; }
 * 
 * @OneToMany(mappedBy = "eggsLocation", cascade = CascadeType.ALL,
 * fetch=FetchType.LAZY)
 * 
 * @JsonIgnore Set<Post> post; }
 */