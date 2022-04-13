package com.be.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_post")
public class Post implements Comparable<Post>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long numberOfEggs;
	private Long totalCost;
	private String shedNumber;
	private Date stockDate;
	private String eggStrength;
	private Integer eggSize;
	
	private Boolean sold;
	
	@ManyToOne
	@JoinColumn(name="profile_id", referencedColumnName = "id")
	private Profile profile;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getEggStrength() {
		return eggStrength;
	}

	public void setEggStrength(String eggStrength) {
		this.eggStrength = eggStrength;
	}

	public Integer getEggSize() {
		return eggSize;
	}

	public void setEggSize(Integer eggSize) {
		this.eggSize = eggSize;
	}

	public String getShedNumber() {
		return shedNumber;
	}

	public void setShedNumber(String shedNumber) {
		this.shedNumber = shedNumber;
	}


	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	@Transient
	private Double pricePerItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumberOfEggs() {
		return numberOfEggs;
	}

	public void setNumberOfEggs(Long numberOfEggs) {
		this.numberOfEggs = numberOfEggs;
	}

	public Long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Long totalCost) {
		this.totalCost = totalCost;
	}

	public Double getPricePerItem() {
		double price = this.totalCost / this.numberOfEggs;
		return price;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "jt_post_eggsType", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id", unique = false), 
		inverseJoinColumns = @JoinColumn(name = "eggType_id", referencedColumnName = "id", unique = false))
	List<EggType> eggsType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "jt_posts_user", joinColumns = @JoinColumn(name="post_id"), inverseJoinColumns = @JoinColumn(name="user_id"))
	User user;

	@UpdateTimestamp
	@JsonIgnore
	private Timestamp postedOn;
	
	@Transient
	private Date postedDate;

	
	public Date getPostedDate() {
		return new Date(this.postedOn.getTime());
	}


	public List<EggType> getEggsType() { return eggsType; }
	  
	public void setEggsType(List<EggType> eggsType) { this.eggsType = eggsType; }
	 

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Timestamp postedOn) {
		this.postedOn = postedOn;
	}

	public void setPricePerItem(Double pricePerItem) {
		this.pricePerItem = pricePerItem;
	}

	@Override
	public int compareTo(Post o) {
		// TODO Auto-generated method stub
		return -1*o.getPostedOn().compareTo(o.getPostedOn());
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}

}
