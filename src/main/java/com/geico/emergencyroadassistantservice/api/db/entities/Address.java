package com.geico.emergencyroadassistantservice.api.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table (name = "address")
@JsonInclude(Include. NON_NULL)
public class Address {

	@JsonIgnore
	@Id
	@GeneratedValue
	
	private Long id;
	
	@Column(name = "address1")
	private String address1;
	
	@Column(name = "address2")
	private String address2;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "country")
	private String country;
	
	
	


}