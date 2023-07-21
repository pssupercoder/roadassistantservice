package com.geico.emergencyroadassistantservice.api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geico.emergencyroadassistantservice.api.db.entities.Assistant;
import com.geico.emergencyroadassistantservice.api.db.entities.Customer;
import com.geico.emergencyroadassistantservice.api.db.entities.Geolocation;
import com.geico.emergencyroadassistantservice.api.db.repositories.AssistantRepository;
import com.geico.emergencyroadassistantservice.api.db.repositories.CustomerRepository;
import com.geico.emergencyroadassistantservice.api.exceptions.GeicoException;
import com.geico.emergencyroadassistantservice.api.services.RoadsideAssistanceService;
import com.geico.emergencyroadassistantservice.api.services.RoadsideAssistanceServiceOrch;
import com.geico.emergencyroadassistantservice.api.utilities.CommonUtilities;
import com.geico.emergencyroadassistantservice.api.utilities.ErrorCodes;

@Service
public class RoadsideAssistanceServiceOrchImpl implements RoadsideAssistanceServiceOrch {

	private static final Logger LOG = LoggerFactory.getLogger(RoadsideAssistanceServiceOrchImpl.class);

	@Autowired
	private AssistantRepository assistantRepository;
	
	@Autowired
	private RoadsideAssistanceService roadsideAssistanceService;
	
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public void updateAssistantLocation(String assistantGuid, Geolocation assistantLocation) throws GeicoException {
		
		Assistant assistant= assistantRepository.findByGuid(assistantGuid);
		
		if(assistant==null) {
			throw  CommonUtilities.createException(ErrorCodes.ASSISTANT_NOT_FOUND, ErrorCodes.MSG_ASSISTANT_NOT_FOUND);
		}
		
		roadsideAssistanceService.updateAssistantLocation(assistant, assistantLocation);
		
	}

	
	public List<Assistant> findNearestAssistants(Geolocation geolocation, int limit) {
		SortedSet<Assistant> findNearestAssistants = roadsideAssistanceService.findNearestAssistants(geolocation, limit);
		return  findNearestAssistants.stream().collect(Collectors.toList());
	}
	
	public Optional<Assistant> reserveAssistant(String customerGuid, Geolocation customerLocation) throws GeicoException {
		
		Customer customer= customerRepository.findByGuid(customerGuid);
		
		if(customer==null) {
			throw  CommonUtilities.createException(ErrorCodes.CUSTOMER_NOT_FOUND, ErrorCodes.MSG_CUSOTMER_NOT_FOUND);
		}
	
		return roadsideAssistanceService.reserveAssistant(customer, customerLocation);
		
	}

	
	public void releaseAssistant(String customerGuid)throws GeicoException {
		
		Customer customer= customerRepository.findByGuid(customerGuid);
        
		if(customer==null) {
			throw  CommonUtilities.createException(ErrorCodes.CUSTOMER_NOT_FOUND, ErrorCodes.MSG_CUSOTMER_NOT_FOUND);
		}
        
        roadsideAssistanceService.releaseAssistant(customer, null);
		
	}
	
	
}
