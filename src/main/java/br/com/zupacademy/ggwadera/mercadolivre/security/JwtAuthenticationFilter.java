package br.com.zupacademy.ggwadera.mercadolivre.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String JWT_HEADER = "Authorization";
  private static final String JWT_PREFIX = "Bearer ";

  private final TokenService tokenService;
  private final UsersService usersService;

  public JwtAuthenticationFilter(TokenService tokenService, UsersService usersService) {
    this.tokenService = tokenService;
    this.usersService = usersService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    Optional<String> token = getTokenFromRequest(request);
    if (token.isPresent()) {
      String email = tokenService.getSubject(token.get());
      UserDetails userDetails = usersService.loadUserByUsername(email);
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> getTokenFromRequest(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(JWT_HEADER))
        .filter(this::validateHeader)
        .map(this::getToken)
        .filter(tokenService::isValid);
  }

  private boolean validateHeader(String header) {
    return header.startsWith(JWT_PREFIX);
  }

  private String getToken(String header) {
    return header.substring(JWT_PREFIX.length());
  }
}
