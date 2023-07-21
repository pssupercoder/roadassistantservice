package com.geico.emergencyroadassistantservice.api.db.repositories;


import java.math.BigDecimal;

import org.springframework.data.repository.CrudRepository;

import com.geico.emergencyroadassistantservice.api.db.entities.Assistant;
import com.geico.emergencyroadassistantservice.api.db.entities.Geolocation;

public interface GeolocationRepository extends CrudRepository<Geolocation, Long> {

	Geolocation findById(long id);
	
	Geolocation findByLatitudeAndLongitude(BigDecimal latitude,BigDecimal longitude);
	
}
