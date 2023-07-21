package com.geico.emergencyroadassistantservice.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geico.emergencyroadassistantservice.api.db.entities.Assistant;
import com.geico.emergencyroadassistantservice.api.db.entities.AssitantGeolocation;
import com.geico.emergencyroadassistantservice.api.db.entities.Customer;
import com.geico.emergencyroadassistantservice.api.db.entities.Geolocation;
import com.geico.emergencyroadassistantservice.api.db.repositories.AssistantGeoLocationRepository;
import com.geico.emergencyroadassistantservice.api.db.repositories.AssistantRepository;
import com.geico.emergencyroadassistantservice.api.db.repositories.CustomerRepository;
import com.geico.emergencyroadassistantservice.api.db.repositories.GeolocationRepository;
import com.geico.emergencyroadassistantservice.api.exceptions.GeicoException;
import com.geico.emergencyroadassistantservice.api.services.RoadsideAssistanceServiceOrch;
import com.geico.emergencyroadassistantservice.api.utilities.CommonUtilities;
import com.geico.emergencyroadassistantservice.api.utilities.ErrorCodes;

@RestController
@RequestMapping("/")
public class EmegergencyAssistantService {

	
	private static final Logger LOG = LoggerFactory.getLogger(EmegergencyAssistantService.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AssistantRepository assistantRepository;
	
	@Autowired
	private GeolocationRepository geolocationRepository;

	@Autowired
	private RoadsideAssistanceServiceOrch roadsideAssistanceServiceOrch;
	
	@Autowired
	private AssistantGeoLocationRepository assistantGeoLocationRepository;
	
	
	@PostMapping(value = "v1/assistants/{assistantGuid}/location", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> updateAssistantGeoLocation(@PathVariable("assistantGuid") String assistantGuid, @RequestBody Geolocation geolocation) throws GeicoException{
		
		if(geolocation==null ) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_GEOLOCATION);
		}
		if(StringUtils.isBlank(assistantGuid)) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_ASSISTANT_ID);
		}

		roadsideAssistanceServiceOrch.updateAssistantLocation(assistantGuid, geolocation);
		
		return  new ResponseEntity<>(HttpStatus.OK);
			
	}
	
	@PostMapping(value = "v1/customers/{customerGuid}/reserveAssistant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> reserveAssistantForCustomer(@PathVariable("customerGuid") String customerGuid, @RequestBody Geolocation geolocation) throws GeicoException{
		
		if(StringUtils.isBlank(customerGuid)) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_CUSTOMER_ID);
		}
		if(geolocation==null ) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_GEOLOCATION);
		}
		roadsideAssistanceServiceOrch.reserveAssistant(customerGuid, geolocation);
		
		return  new ResponseEntity<>( HttpStatus.OK);
			
	}
	
	@PostMapping(value = "v1/customers/{customerGuid}/assistant/{assistantGuid}/releaseAssistant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> releaseAssistantForCustomer(@PathVariable("customerGuid") String customerGuid, @PathVariable("assistantGuid") String assistantGuid) throws GeicoException{
		
		if(StringUtils.isBlank(customerGuid)) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_CUSTOMER_ID);
		}
		if(StringUtils.isBlank(assistantGuid)) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_ASSISTANT_ID);
		}
		roadsideAssistanceServiceOrch.releaseAssistant(customerGuid, assistantGuid);
		
		return  new ResponseEntity<>(HttpStatus.OK);
			
	}
	
	
	@GetMapping(value = "v1/assistants/find", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Assistant>> findNearByAssitants( @RequestParam("limit") int limit, @RequestBody Geolocation geolocation) throws GeicoException{
		
		if(geolocation==null ) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_GEOLOCATION);
		}
		if(limit<=0) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_LIMIT);
		}
		
		List<Assistant> list = roadsideAssistanceServiceOrch.findNearestAssistants( geolocation, limit);
	
		return  new ResponseEntity<>(list, HttpStatus.OK);
			
	}
	
	@PostMapping(value = "v1/assistants", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> createAssistant(@RequestBody Assistant assistant) throws GeicoException {
		
		if(assistant==null ) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_ASSISTANT);
		}
		if(assistantRepository.findByFirstNameAndLastName(assistant.getFirstName(),assistant.getLastName())!=null){
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_ASSISTANT_ALREADY_EXIST);
		}
		
	   assistant.setGuid(UUID.randomUUID().toString());
	   Assistant newAssistant= assistantRepository.save(assistant);
	   
	   return new ResponseEntity<>(newAssistant.getGuid(),HttpStatus.CREATED);
			
	}
	
	@PostMapping(value = "v1/customers", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE}) 
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer) throws GeicoException{
		
		if(customer==null ) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_INVALID_CUSTOMER);
		}
		
		if(customerRepository.findByFirstNameAndLastName(customer.getFirstName(),customer.getLastName())!=null){
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_CUSOMETR_ALREADY_EXIST);
		}
		
		customer.setGuid(UUID.randomUUID().toString());
		Customer newCustomer= customerRepository.save(customer);
			
		return new ResponseEntity<>(newCustomer.getGuid(), HttpStatus.CREATED);
			
	}
	
	@PostMapping(value = "v1/geolocations", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> createGeolocation(@RequestBody List<Geolocation> geoLocation) throws GeicoException{
		

		if(geoLocation==null || geoLocation.size()==0) {
			throw CommonUtilities.createException(ErrorCodes.INVALID_INPUT, ErrorCodes.MSG_EMPTY_GEOLOCATION_LIST);
		}
		
		
		geolocationRepository.saveAll(geoLocation);
			
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	
	@GetMapping(value = "v1/reservations", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<AssitantGeolocation>> reservationDetails(){
		
		Optional<List<AssitantGeolocation>> assitantGeolocationList=
			  assistantGeoLocationRepository.findAllByCustomerIdNotNull();
	
	
		return  new ResponseEntity<>(assitantGeolocationList.get(), HttpStatus.OK);
			
	}
	

	@GetMapping(value = "v1/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Customer>> getCustomers(){
		
		Iterable<Customer> customers= customerRepository.findAll();
	
	
	   return  new ResponseEntity<>(StreamSupport
			  .stream(customers.spliterator(), false)
			  .collect(Collectors.toList()), HttpStatus.OK);
			
	}
	

	@GetMapping(value = "v1/assistants", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Customer>> getAssistants(){
		
		Iterable<Customer> customers= customerRepository.findAll();
	
	
	   return  new ResponseEntity<>(StreamSupport
			  .stream(customers.spliterator(), false)
			  .collect(Collectors.toList()), HttpStatus.OK);
			
	}
}
