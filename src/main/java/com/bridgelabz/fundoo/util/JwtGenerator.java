package com.bridgelabz.fundoo.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@SuppressWarnings("serial")
@Component
public class JwtGenerator implements Serializable {
	private static final String SECRET = "9876543210";

	public String generateToken(long l) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", l).sign(Algorithm.HMAC512(SECRET));
		} catch (Exception e) {

		}
		return token;
	}

	public long parseToken(String token) {
		long id = 0;
		if (token != null) {
				try {
					id=JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token).getClaim("id").asInt();
				} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return id;
	}
}