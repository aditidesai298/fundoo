package com.bridgelabz.fundoo.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

@SuppressWarnings("serial")
@Component
public class JwtGenerator implements Serializable {
	private static final String SECRET = "9876543210";

	public String generateToken(long id) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", id).sign(Algorithm.HMAC512(SECRET));
		} catch (Exception e) {

		}
		return token;
	}

	public Long decodeToken(String jwtToken) {
		Long userId = (long) 0;
		try {
			if (jwtToken != null) {

				userId = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwtToken).getClaim("id").asLong();
			}
		} catch (IllegalArgumentException | JWTCreationException | UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return userId;

	}
}