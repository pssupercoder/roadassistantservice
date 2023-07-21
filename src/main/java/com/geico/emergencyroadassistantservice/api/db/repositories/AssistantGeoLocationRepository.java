package com.geico.emergencyroadassistantservice.api.db.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.geico.emergencyroadassistantservice.api.db.entities.AssitantGeolocation;

public interface AssistantGeoLocationRepository extends CrudRepository<AssitantGeolocation, Long> {

	AssitantGeolocation findByAssistantId(long assistantId);
	AssitantGeolocation findByCustomerId(long customerId);
	
	Optional<List<AssitantGeolocation>> findAllByCustomerId(Long customerId);
	Optional<List<AssitantGeolocation>> findAllByCustomerIdNotNull();
}
