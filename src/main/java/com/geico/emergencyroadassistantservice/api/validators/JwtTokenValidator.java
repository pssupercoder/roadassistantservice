package com.geico.emergencyroadassistantservice.api.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;

@Component
public class JwtTokenValidator {

	private static final Logger LOG = LoggerFactory.getLogger(JwtTokenValidator.class);

	@Autowired
	private JwtParser jwtParser;

	public boolean valiadate(final String token) {
		boolean result = false;

		try {
			String jwtToken = token.split(" ")[1];
			Jws<Claims> claims = jwtParser.parseClaimsJws(jwtToken);
			if (claims != null) {

				Claims claim = claims.getBody();
				//Authorization Pending
			}
			result = true;
		} catch (ExpiredJwtException expiredJwtException) {
			LOG.error(expiredJwtException.getMessage());
		} catch (Exception exception) {
			LOG.error(exception.getMessage());
		}

		return result;

	}

}
