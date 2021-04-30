package br.com.zupacademy.ggwadera.mercadolivre.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(
    value = "/api/auth",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

  private final AuthenticationManager authManager;
  private final TokenService tokenService;

  @Autowired
  public AuthenticationController(AuthenticationManager authManager, TokenService tokenService) {
    this.authManager = authManager;
    this.tokenService = tokenService;
  }

  @PostMapping
  public ResponseEntity<Map<String, String>> authenticate(@RequestBody @Valid LoginDTO loginInfo) {
    UsernamePasswordAuthenticationToken authenticationToken = loginInfo.toAuthentication();
    Authentication authentication = authManager.authenticate(authenticationToken);
    String jwt = tokenService.generateToken(authentication);
    return ResponseEntity.ok(Collections.singletonMap("jwt", jwt));
  }
}
