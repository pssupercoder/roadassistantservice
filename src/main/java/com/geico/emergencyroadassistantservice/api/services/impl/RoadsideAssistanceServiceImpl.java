package com.geico.emergencyroadassistantservice.api.services.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geico.emergencyroadassistantservice.api.db.entities.Assistant;
import com.geico.emergencyroadassistantservice.api.db.entities.AssitantGeolocation;
import com.geico.emergencyroadassistantservice.api.db.entities.Customer;
import com.geico.emergencyroadassistantservice.api.db.entities.Geolocation;
import com.geico.emergencyroadassistantservice.api.db.repositories.AssistantGeoLocationRepository;
import com.geico.emergencyroadassistantservice.api.exceptions.GeicoException;
import com.geico.emergencyroadassistantservice.api.geofinder.geocode.GeoDistanceCalculator;
import com.geico.emergencyroadassistantservice.api.services.RoadsideAssistanceService;
import com.geico.emergencyroadassistantservice.api.utilities.CommonUtilities;
import com.geico.emergencyroadassistantservice.api.utilities.ErrorCodes;

@Service
public class RoadsideAssistanceServiceImpl implements RoadsideAssistanceService{

	private static final Logger LOG = LoggerFactory.getLogger(RoadsideAssistanceServiceImpl.class);

	
	@Autowired
	private AssistantGeoLocationRepository assistantGeoLocationRepository;
	
	
	
	@Autowired
	private GeoDistanceCalculator geoDistanceCalculator;
	
	
	@Override
	public void updateAssistantLocation(Assistant assistant, Geolocation assistantLocation) {
	
		AssitantGeolocation assitantGeolocation= assistantGeoLocationRepository.findByAssistantId(assistant.getId());
		
		if(assitantGeolocation!=null) {
			
			assitantGeolocation.setAssistant(assistant);
			
			assitantGeolocation.setLatitude(assistantLocation.getLatitude());
			assitantGeolocation.setLongitude(assistantLocation.getLongitude());
			
			assistantGeoLocationRepository.save(assitantGeolocation);
		}
		else {
			assitantGeolocation=new AssitantGeolocation();
			assitantGeolocation.setAssistant(assistant);
			assitantGeolocation.setLatitude(assistantLocation.getLatitude());
			assitantGeolocation.setLongitude(assistantLocation.getLongitude());
			assistantGeoLocationRepository.save(assitantGeolocation);
		}
		
	}

	
	@Override
	public SortedSet<Assistant> findNearestAssistants(Geolocation geolocation, int limit) {
	
		SortedSet<Assistant> set=new TreeSet<>();
		
		Optional<List<AssitantGeolocation>> assitantGeolocationList=
		  assistantGeoLocationRepository.findAllByCustomerId(null);
		
		if(assitantGeolocationList.isEmpty()) {
			return set;
		}
		List<AssitantGeolocation> geoData = assitantGeolocationList.get();
		
		List<AssitantGeolocation> distanceCalculationList = geoData.stream()
			    .peek(f -> f.setDistance(geoDistanceCalculator.findDistance(geolocation.getLatitude().doubleValue(), geolocation.getLongitude().doubleValue(), f.getLatitude().doubleValue(), f.getLongitude().doubleValue())))
			    .sorted(Comparator.comparing(AssitantGeolocation::getDistance))
			    .collect(Collectors.toList());
		
		List<AssitantGeolocation> limitedList=distanceCalculationList.size()>limit? distanceCalculationList.subList(0, limit): distanceCalculationList;
		
		
		if(limitedList!=null && limitedList.size()>0) {
		  for(AssitantGeolocation geoName: limitedList) {
			  
		    Assistant assistant = geoName.getAssistant();
		    assistant.setDistance(geoName.getDistance());
		    
			set.add(assistant);
		  }
		}
		
		return set;
	}

	@Override
	public Optional<Assistant> reserveAssistant(Customer customer, Geolocation customerLocation)throws GeicoException {
		
         AssitantGeolocation assitantGeolocation= assistantGeoLocationRepository.findByCustomerId(customer.getId());
         
		 if(assitantGeolocation!=null) {
			 throw  CommonUtilities.createException(ErrorCodes.REGISTRATION_FOUND, ErrorCodes.MSG_REGISTRATION_FOUND);
			 
		 }
		
		SortedSet<Assistant> assistants= findNearestAssistants(customerLocation, 1);
		
		if(assistants.size()==0) {
			throw  CommonUtilities.createException(ErrorCodes.ASSISTANT_NOT_FOUND, ErrorCodes.MSG_ASSISTANT_NOT_FOUND);
		}
		  
		 assitantGeolocation= assistantGeoLocationRepository.findByAssistantId(assistants.first().getId());
         
		 if(assitantGeolocation==null) {
			 throw  CommonUtilities.createException(ErrorCodes.ASSISTANT_GEOLOCATION_NOT_FOUND, ErrorCodes.MSG_ASSISTANT_GEOLOCATION_NOT_FOUND);
		 }
		 
	  assitantGeolocation.setCustomer(customer);
	  assistantGeoLocationRepository.save(assitantGeolocation);
			
	  return  Optional.of(assistants.first());
		 
	}


	

	@Override
	public void releaseAssistant(Customer customer, Assistant assistant) throws GeicoException{
		 
		 AssitantGeolocation assitantGeolocation= assistantGeoLocationRepository.findByCustomerId(customer.getId());
         
		 if(assitantGeolocation==null) {
			 throw  CommonUtilities.createException(ErrorCodes.REGISTRATION_NOT_FOUND, ErrorCodes.MSG_REGISTRATION_NOT_FOUND);
			 
		 }
		assitantGeolocation.setCustomer(null);
		assistantGeoLocationRepository.save(assitantGeolocation);
			
		
	}
	
	
}
