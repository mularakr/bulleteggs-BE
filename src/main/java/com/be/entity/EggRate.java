package com.be.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_egg_rates")
public class EggRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name="egg_id") public EggType eggType;
	 */
	
	public Double pricePerEgg;
	
//	@UpdateTimestamp
	public Date currentTimestamp;
	
	@ManyToOne
	@JoinColumn(name="location_id")
	@JsonIgnore
	public Location location;
	
	public SellerCategory sellerCategory;
	
}


