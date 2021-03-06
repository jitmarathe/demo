package org.springframework.samples.travel;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A hotel where users may book stays.
 */
/**
 * @author ashish.c.tiwari
 *
 */

@Entity
public class Hotel implements Serializable {
	
	private Long id;

	private String name;

	private String address;

	private String city;

	private String state;

	private String zip;

	private String country;

	private BigDecimal price;
	
	private String rating;
	
	public Hotel(String name, String address, String city, String state, String zip, String country, BigDecimal price, String rating){
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.price = price;
		this.rating = rating;
		
	}
	
	public Hotel(){
		
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Column(precision = 6, scale = 2)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	

	public Booking createBooking(User user) {
		return new Booking(this, user);
	}

	@Override
	public String toString() {
		return "Hotel(" + name + "," + address + "," + city + ","+ rating + ","+ price + "," + zip + ")";
	}

}
