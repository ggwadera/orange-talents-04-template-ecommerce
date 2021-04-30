package br.com.zupacademy.ggwadera.mercadolivre.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

  private final Key key;
  private final long expirationInMillis;

  public TokenService(
      @Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationInMillis) {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    this.expirationInMillis = expirationInMillis;
  }

  private Claims getClaims(String jwt) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
  }

  public String getSubject(String jwt) {
    Claims claims = getClaims(jwt);
    return claims.getSubject();
  }

  public String generateToken(Authentication authentication) {
    final UserDetails user = (UserDetails) authentication.getPrincipal();
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .setIssuer("Desafio Mercado Livre")
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + expirationInMillis))
        .signWith(key)
        .compact();
  }

  public boolean isValid(String jwt) {
    try {
      getClaims(jwt);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
