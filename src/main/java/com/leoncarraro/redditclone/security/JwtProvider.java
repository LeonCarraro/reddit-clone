package com.leoncarraro.redditclone.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.leoncarraro.redditclone.service.exception.KeyStoreLoadException;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {
	
	private KeyStore keyStore;
	
	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/redditclone.jks");
			keyStore.load(resourceAsStream, "redditclonepass".toCharArray());
		} catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
			throw new KeyStoreLoadException("Exception ocurred while loading keystore!");
		}
	}

	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
			.setSubject(principal.getUsername())
			.signWith(getPrivateKey())
			.compact();
	}

	public boolean validateToken(String token) {
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token);
		return true;
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(getPublicKey())
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}
	
	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey("redditclone", "redditclonepass".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new KeyStoreLoadException("Exception ocurred while retrieving private key from keystore!");
		}
	}

	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("redditclone").getPublicKey();
		} catch (KeyStoreException e) {
			throw new KeyStoreLoadException("Exception ocurred while retrieving public key from keystore!");
		}
	}
	
}
