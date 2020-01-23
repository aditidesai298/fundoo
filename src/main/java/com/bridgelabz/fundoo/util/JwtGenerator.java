package com.bridgelabz.fundoo.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

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

	public Long decodeToken(String jwtToken) {
		Long userId = (long) 0;
		
			if (jwtToken != null) {
				Verification verification = null;
				try {
					verification = JWT.require(Algorithm.HMAC256(SECRET));
				} catch (IllegalArgumentException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JWTVerifier jwtverifier = verification.build();
				DecodedJWT decodedjwt = jwtverifier.verify(jwtToken);
				Claim claim = decodedjwt.getClaim("id");
				userId = claim.asLong();
			}
		
		return userId;
	}
}